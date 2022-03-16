package org.x.flower.flow.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class FlowDo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String flowName;

    private Byte isDisable;

    private LocalDateTime nextTriggerTime;

    private LocalDateTime startTime;

    private String trigger;

    private Integer triggerType;

    private Integer fillBackSequence;

    private Integer flowType;

    private String desc;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String dag;

}