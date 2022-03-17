package org.x.flower.domain.flow.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.x.flower.domain.flow.gateway.FlowInstanceGateway;
import org.x.flower.domain.flow.model.Flow;
import org.x.flower.domain.flow.model.FlowInstance;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlowInstanceService {

    @Resource
    private FlowService flowService;

    @Resource
    private FlowInstanceGateway flowInstanceGateway;

    public List<FlowInstance> initInstance(List<Flow> flows) {
        ArrayList<FlowInstance> list = Lists.newArrayList();
        flows.forEach(flow -> {
            Optional<FlowInstance> flowInstance = this.findByFlowIdAndScheduleTriggerTime(flow.getId(), flow.getNextTriggerTime());
            if (!flowInstance.isPresent()) {
                FlowInstance instance = save(flow);
                list.add(instance);
                flowService.refreshScheduleTriggerTime(flow);
            }
        });
        return list;
    }

    public FlowInstance save(Flow flow) {
        return null;
    }

    public Optional<FlowInstance> findByFlowIdAndScheduleTriggerTime(Long flowId, LocalDateTime time) {
        FlowInstance instance = new FlowInstance();
        instance.setFlowId(flowId)
                .setScheduleTriggerTime(time);
        return flowInstanceGateway.selectOne(instance);
    }
}
