package org.x.flower.flow.schedule;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static org.x.flower.common.Constants.X_POOL;

@Slf4j
@Service
public class ScheduleService {

    private static final int FIXED_DELAY= 15000;

    @Async(X_POOL)
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void schedule() {
        Stopwatch stopwatch = Stopwatch.createStarted();
    }
}
