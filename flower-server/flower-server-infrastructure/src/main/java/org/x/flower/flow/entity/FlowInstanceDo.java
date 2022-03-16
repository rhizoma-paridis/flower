package org.x.flower.flow.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class FlowInstanceDo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Date scheduleTriggerTime;

    private Date actualTriggerTime;

    private Date finishedTime;

    private Long instanceId;

    private Long flowId;

    private Integer state;

    private Integer runningTimes;

    private Date createTime;

    private Date updateTime;


}