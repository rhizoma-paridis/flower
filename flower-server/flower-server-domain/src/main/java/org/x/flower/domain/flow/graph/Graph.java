package org.x.flower.domain.flow.graph;

import lombok.Data;
import lombok.experimental.Accessors;
import org.x.flower.domain.flow.Job;

import java.util.List;

@Data
@Accessors(chain = true)
public class Graph {

    private List<Job> nodes;

    private List<Edge> edges;
}
