package org.x.flower.flow.schedule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeWheelService {

    public static void schedule(Long uniqueId, Long delayMS, TimerTasker tasker) {
        log.info("schedule");
    }
}
