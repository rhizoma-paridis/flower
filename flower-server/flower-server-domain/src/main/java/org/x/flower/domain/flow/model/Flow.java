package org.x.flower.domain.flow.model;

import com.alibaba.cola.domain.Entity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.x.flower.domain.flow.model.graph.Graph;
import org.x.flower.domain.flow.model.trigger.Trigger;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
public class Flow implements Job{

    private Long id;

    private String flowName;

    private Boolean isDisable;

    private LocalDateTime nextTriggerTime;

    private LocalDateTime startTime;

    private String scheduleInterval;

    private Integer triggerType;

    private Integer fillBackSequence;

    private Integer flowType;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String dag;
    /*private Trigger trigger;
    private Graph dag;*/

    @Override
    public boolean run() {
        return false;
    }

    public String show() {
        return dag.toString();
    }

    public Flow refreshScheduleTriggerTime() {
        return null;
    }
}
