package org.x.flower.domain.flow.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.x.flower.domain.flow.model.Flow;
import org.x.flower.domain.flow.model.FlowInstance;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FlowInstanceService {

    @Resource
    private FlowService flowService;

    public List<FlowInstance> initInstance(List<Flow> flows) {
        ArrayList<FlowInstance> list = Lists.newArrayList();
        flows.forEach(flow -> {
            FlowInstance instance = save(flow);
            list.add(instance);
            flowService.refreshScheduleTriggerTime(flow);
        });
        return list;
    }

    public FlowInstance save(Flow flow) {
        return null;
    }
}
