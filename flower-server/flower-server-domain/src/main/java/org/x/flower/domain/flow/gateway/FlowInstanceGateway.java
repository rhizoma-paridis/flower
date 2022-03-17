package org.x.flower.domain.flow.gateway;

import org.x.flower.domain.flow.model.FlowInstance;

import java.util.Optional;

public interface FlowInstanceGateway {

    Optional<FlowInstance> selectOne(FlowInstance instance);
}
