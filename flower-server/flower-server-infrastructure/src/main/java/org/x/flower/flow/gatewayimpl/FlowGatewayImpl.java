package org.x.flower.flow.gatewayimpl;

import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.x.flower.domain.flow.gateway.FlowGateway;
import org.x.flower.domain.flow.model.Flow;
import org.x.flower.flow.entity.FlowDo;
import org.x.flower.flow.mapper.FlowDynamicSqlSupport;
import org.x.flower.flow.mapper.FlowMapper;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mybatis.dynamic.sql.SqlBuilder.isLessThan;
import static org.mybatis.dynamic.sql.SqlBuilder.isTrue;

@Component
public class FlowGatewayImpl implements FlowGateway {

    @Resource
    private FlowMapper flowMapper;

    @Override
    public List<Flow> findWaitTriggerFlows(LocalDateTime timeThreshold) {
        List<FlowDo> list = flowMapper.select(c -> c.where(FlowDynamicSqlSupport.isDisable, isTrue())
                .and(FlowDynamicSqlSupport.nextTriggerTime, isLessThan(timeThreshold))
        );
        ArrayList<Flow> flows = Lists.newArrayList();
        BeanUtils.copyProperties(list, flows);
        return flows;
    }

    @Override
    public void update(Flow flow) {
        FlowDo flowDo = new FlowDo();
        BeanUtils.copyProperties(flow, flowDo);
        flowMapper.updateByPrimaryKey(flowDo);
    }
}
