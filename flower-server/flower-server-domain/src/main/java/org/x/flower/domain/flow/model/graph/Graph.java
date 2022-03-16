package org.x.flower.domain.flow.model.graph;

import lombok.Data;
import lombok.experimental.Accessors;
import org.x.flower.domain.flow.model.Job;

import java.util.List;

@Data
@Accessors(chain = true)
public class Graph {

    private List<Job> nodes;

    private List<Edge> edges;
}
