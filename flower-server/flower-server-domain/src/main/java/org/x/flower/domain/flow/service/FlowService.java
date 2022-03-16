package org.x.flower.domain.flow.service;

import org.springframework.stereotype.Service;
import org.x.flower.domain.flow.gateway.FlowGateway;
import org.x.flower.domain.flow.model.Flow;

import javax.annotation.Resource;

@Service
public class FlowService {

    @Resource
    private FlowGateway flowGateway;

    public void refreshScheduleTriggerTime(Flow flow) {
        flow.refreshScheduleTriggerTime();
        flowGateway.update(flow);
    }
}
