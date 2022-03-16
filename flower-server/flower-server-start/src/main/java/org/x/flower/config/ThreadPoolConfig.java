package org.x.flower.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.x.flower.common.Constants;
import org.x.flower.common.RejectedExecutionHandlerFactory;

import java.util.concurrent.Executor;

import static org.x.flower.common.Constants.*;

@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
public class ThreadPoolConfig {

    @Bean(X_POOL)
    public Executor getPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 4);
        // use SynchronousQueue
        executor.setQueueCapacity(0);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("xPool-");
        executor.setRejectedExecutionHandler(RejectedExecutionHandlerFactory.newThreadRun("flowerTiming"));
        return executor;
    }

    @Bean("xBackgroundPool")
    public Executor initBackgroundPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() * 8);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 16);
        executor.setQueueCapacity(8192);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("xBackgroundPool-");
        executor.setRejectedExecutionHandler(RejectedExecutionHandlerFactory.newReject("flowerBackgroundPool"));
        return executor;
    }

    // 引入 WebSocket 支持后需要手动初始化调度线程池
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        scheduler.setThreadNamePrefix("xSchedulePool-");
        scheduler.setDaemon(true);
        return scheduler;
    }
}
