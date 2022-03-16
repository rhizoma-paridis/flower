package org.x.flower.flow.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.x.flower.domain.flow.gateway.FlowGateway;
import org.x.flower.domain.flow.model.Flow;
import org.x.flower.domain.flow.model.FlowInstance;
import org.x.flower.domain.flow.service.FlowInstanceService;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import static org.x.flower.common.Constants.X_POOL;

@Slf4j
@Service
public class ScheduleService {

    private static final int FIXED_DELAY= 15000;

    @Resource
    private FlowGateway flowGateway;

    @Resource
    private FlowInstanceService flowInstanceService;

    @Async(X_POOL)
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void schedule() {
        LocalDateTime timeThreshold = LocalDateTime.now().plus(Duration.ofMillis(FIXED_DELAY));
        List<Flow> flows = flowGateway.findWaitTriggerFlows(timeThreshold);
        List<FlowInstance> flowInstances = flowInstanceService.initInstance(flows);

        doSchedule(flowInstances);
    }

    public void doSchedule(List<FlowInstance> flowInstances) {
        flowInstances.forEach(instance -> {
            long delay = getDelay(instance);
            TimeWheelService.schedule(instance.getInstanceId(), delay, () -> {});
        });
    }

    private long getDelay(FlowInstance instance) {
        long now = Instant.now().toEpochMilli();
        long scheduleTriggerTime = instance.getScheduleTriggerTime();
        long delay = 0;
        if (scheduleTriggerTime < now) {
            log.warn("[flow-{}] schedule delay, schedule: {}, now: {}", instance.getId(), scheduleTriggerTime, now);
        } else {
            delay = scheduleTriggerTime - now;
        }
        return delay;
    }
}
