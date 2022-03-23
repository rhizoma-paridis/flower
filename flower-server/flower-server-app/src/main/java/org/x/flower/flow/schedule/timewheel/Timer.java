package org.x.flower.flow.schedule.timewheel;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface Timer {

    TaskFeature addTask(Task task, long delay, TimeUnit unit);

    Set<Task> stop();
}
