package org.x.flower.domain.flow.gateway;

import org.x.flower.domain.flow.model.Flow;

import java.time.LocalDateTime;
import java.util.List;

public interface FlowGateway {

    List<Flow> findWaitTriggerFlows(LocalDateTime timeThreshold);

    void update(Flow flow);
}
