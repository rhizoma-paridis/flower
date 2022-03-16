package org.x.flower.domain.flow.model.graph;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Edge {

    private Long source;
    private Long target;

}
