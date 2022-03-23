package org.x.flower.flow.schedule.timewheel;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.x.flower.common.RejectedExecutionHandlerFactory;

import java.time.Instant;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public class TimerWheel implements Timer {
    private final long tickDuration;

    private final HashedWheelSlot[] wheel;
    private final int mask;

    private final ExecutorService taskProcessPool;

    private final LinkedBlockingDeque<HashedTaskFeature> waitingTasks = Queues.newLinkedBlockingDeque();

    private final Indicator indicator;
    private final long startTime;

    private AtomicBoolean stop = new AtomicBoolean(false);
    private final CountDownLatch latch = new CountDownLatch(2);

    private static final int MAXIMUM_CAPACITY = 1 << 30;


    public TimerWheel(long tickDuration, int ticksPerWheel) {
        this(tickDuration, ticksPerWheel, 0);
    }

    public TimerWheel(long tickDuration, int ticksPerWheel, int processThreadNum) {
        this.tickDuration = tickDuration;
        int slotNum = normalizeTicksPerWheel(ticksPerWheel);
        wheel = buildWheel(slotNum);
        mask = wheel.length - 1;

        taskProcessPool = buildPool(processThreadNum);

        startTime = Instant.now().toEpochMilli();
        indicator = new Indicator();
        new Thread(indicator, "TimerWheel-indicator").start();
        new Thread(new Gear(), "TimerWheel-gear").start();
    }


    private ExecutorService buildPool(int processThreadNum) {
        final ExecutorService taskProcessPool;
        if (processThreadNum <= 0) {
            taskProcessPool = null;
        } else {
            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("TimerWheel-executor-%d").build();
            BlockingDeque<Runnable> queue = Queues.newLinkedBlockingDeque(16);
            int core = Math.max(Runtime.getRuntime().availableProcessors(), processThreadNum);
            taskProcessPool = new ThreadPoolExecutor(core,
                    4 * core,
                    60,
                    TimeUnit.SECONDS,
                    queue,
                    threadFactory,
                    RejectedExecutionHandlerFactory.newCallerRun("TimeWheelPool"));

        }
        return taskProcessPool;
    }

    private static int normalizeTicksPerWheel(int ticksPerWheel) {

        int n = ticksPerWheel - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    private HashedWheelSlot[] buildWheel(int ticksPerWheel) {
        HashedWheelSlot[] wheel = new HashedWheelSlot[ticksPerWheel];
        for (int i = 0; i < ticksPerWheel; i++) {
            wheel[i] = new HashedWheelSlot();
        }
        return wheel;
    }

    @Override
    public TaskFeature addTask(Task task, long delay, TimeUnit unit) {
        long targetTime = Instant.now().toEpochMilli() + unit.toMillis(delay);
        HashedTaskFeature taskFeature = new HashedTaskFeature(task, targetTime);
        waitingTasks.add(taskFeature);
        return taskFeature;
    }

    @Override
    public Set<Task> stop() {
        stop.set(true);
        if (Objects.nonNull(taskProcessPool)) {
            taskProcessPool.shutdown();
        }
        while (!taskProcessPool.isTerminated()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getUnprocessedTasks();
    }

    private Set<Task> getUnprocessedTasks() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Set<Task> tasks = Sets.newHashSet();
        Consumer<HashedTaskFeature> consumer = t -> {
            if (t.status.get() == HashedTaskFeature.WAITING) {
                tasks.add(t.task);
            }
        };

        waitingTasks.forEach(consumer);
        for (HashedWheelSlot slot : wheel) {
            slot.forEach(consumer);
        }
        return tasks;
    }

    private final class HashedWheelSlot extends LinkedList<HashedTaskFeature> {

        public void expireTasks(long currentTick) {
            this.removeIf(taskFeature -> {
                if (taskFeature.status.get() == HashedTaskFeature.CANCELED) {
                    return true;
                }

                if (taskFeature.status.get() != HashedTaskFeature.WAITING) {
                    log.warn("taskFeature status: {}, impossible", taskFeature.status);
                    return true;
                }

                if (taskFeature.ticks <= currentTick) {
                    if (taskFeature.ticks < currentTick) {
                        log.warn("taskFeature should be run in past but not, fix it. taskFeature.tick: {}, currentTick: {}", taskFeature.ticks, currentTick);
                    }

                    try {
                        runTask(taskFeature);
                    } catch (Exception e) {
                        log.info("run task error", e);
                    }
                    return true;
                }
                return false;
            });
        }
    }

    private void runTask(HashedTaskFeature taskFeature) {
        taskFeature.status.set(HashedTaskFeature.RUNNING);
        if (taskProcessPool == null) {
            taskFeature.task.run();
        } else {
            taskProcessPool.submit(taskFeature.task);
        }
    }

    private class Indicator implements Runnable {
        private long tick = 0;

        @Override
        public void run() {
            while (!stop.get()) {

                tickTack();
                int currentIndex = (int) (tick & mask);
                HashedWheelSlot slot = wheel[currentIndex];
                slot.expireTasks(tick);
                tick++;
            }
            latch.countDown();
        }

        private void tickTack() {

            // 下一次调度的绝对时间
            long nextTime = startTime + (tick + 1) * tickDuration;
            long sleepTime = nextTime - System.currentTimeMillis();

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (Exception ignore) {
                }
            }
        }


    }

    private final class Gear implements Runnable {

        @Override
        public void run() {
            pushTaskToSlot();
        }

        private void pushTaskToSlot() {

            while (!stop.get()) {
                HashedTaskFeature taskFeature = null;
                try {
                    taskFeature = waitingTasks.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 总共的偏移量
                long offset = taskFeature.targetTime - startTime;
                // 总共需要走的指针步数
                taskFeature.ticks = offset / tickDuration;
                // 取余计算 slot index
                int index = (int) (taskFeature.ticks & mask);
                HashedWheelSlot slot = wheel[index];

                // TimerTask 维护 slot 引用，用于删除该任务
                taskFeature.slot = slot;

                if (taskFeature.status.get() == HashedTaskFeature.WAITING) {
                    slot.add(taskFeature);
                }
            }
            latch.countDown();
        }
    }


    private final class HashedTaskFeature implements TaskFeature {

        private Task task;

        private long targetTime;

        private HashedWheelSlot slot;
        /**
         *
         */
        private long ticks;

        private AtomicInteger status = new AtomicInteger(NONE);


        private static final int NONE = 0;
        private static final int WAITING = 1;
        private static final int RUNNING = 2;
        private static final int FINISHED = 3;
        private static final int CANCELED = 4;

        public HashedTaskFeature(Task task, long targetTime) {
            this.task = task;
            this.targetTime = targetTime;
            this.status.set(WAITING);
        }

        @Override
        public Task getTask() {
            return task;
        }

        @Override
        public boolean cancel() {
            if (status.get() == WAITING) {
                status.set(CANCELED);
                return slot.remove(this);
            }
            return false;
        }

        @Override
        public boolean isCanceled() {
            return status.get() == CANCELED;
        }
    }
}
