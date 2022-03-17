package org.x.flower.domain.flow.model;

import com.alibaba.cola.domain.Entity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
public class FlowInstance {

    private Long id;

    private LocalDateTime scheduleTriggerTime;

    private LocalDateTime actualTriggerTime;

    private LocalDateTime finishedTime;

    private Long instanceId;

    private Long flowId;

    private Integer state;

    private Integer runningTimes;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public long parseScheduleTriggerTime2Long() {
        return Timestamp.valueOf(this.scheduleTriggerTime).getTime();
    }

}
