package org.x.flower.flow.gatewayimpl;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.x.flower.domain.flow.gateway.FlowInstanceGateway;
import org.x.flower.domain.flow.model.FlowInstance;
import org.x.flower.flow.entity.FlowInstanceDo;
import org.x.flower.flow.mapper.FlowInstanceDynamicSqlSupport;
import org.x.flower.flow.mapper.FlowInstanceMapper;

import javax.annotation.Resource;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Component
public class FlowInstanceGatewayImpl implements FlowInstanceGateway {

    @Resource
    private FlowInstanceMapper flowInstanceMapper;

    @Override
    public Optional<FlowInstance> selectOne(FlowInstance instance) {
        Optional<FlowInstanceDo> flowInstanceDo = flowInstanceMapper.selectOne(c ->
                c.where(FlowInstanceDynamicSqlSupport.flowId, isEqualToWhenPresent(instance::getFlowId))
                        .and(FlowInstanceDynamicSqlSupport.scheduleTriggerTime, isEqualToWhenPresent(instance::getScheduleTriggerTime))
        );
        if (flowInstanceDo.isPresent()) {
            return Optional.empty();
        }
        FlowInstance fl = new FlowInstance();
        flowInstanceDo.ifPresent(f -> BeanUtils.copyProperties(f, fl));
        return Optional.of(fl);
    }

}
