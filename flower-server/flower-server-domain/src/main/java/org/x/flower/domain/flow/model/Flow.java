package org.x.flower.domain.flow.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.x.flower.domain.flow.model.graph.Graph;
import org.x.flower.domain.flow.model.trigger.Trigger;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Flow implements Job{

    private Long id;
    private String name;
    private Trigger trigger;
    private Graph dag;
    private String desc;
    /**
     * 1:success, 2:failed, 3:skip, 4:queued, 5:none, 6:running, 7:upstream_failed
     */
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTIme;


    @Override
    public boolean run() {
        return false;
    }

    public String show() {
        return dag.toString();
    }
}
