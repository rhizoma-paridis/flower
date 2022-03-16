package org.x.flower.flow.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class FlowInstanceDynamicSqlSupport {
    public static final FlowInstance flowInstance = new FlowInstance();

    public static final SqlColumn<Long> id = flowInstance.id;

    public static final SqlColumn<LocalDateTime> scheduleTriggerTime = flowInstance.scheduleTriggerTime;

    public static final SqlColumn<LocalDateTime> actualTriggerTime = flowInstance.actualTriggerTime;

    public static final SqlColumn<LocalDateTime> finishedTime = flowInstance.finishedTime;

    public static final SqlColumn<Long> instanceId = flowInstance.instanceId;

    public static final SqlColumn<Long> flowId = flowInstance.flowId;

    public static final SqlColumn<Integer> state = flowInstance.state;

    public static final SqlColumn<Integer> runningTimes = flowInstance.runningTimes;

    public static final SqlColumn<LocalDateTime> createTime = flowInstance.createTime;

    public static final SqlColumn<LocalDateTime> updateTime = flowInstance.updateTime;

    public static final class FlowInstance extends AliasableSqlTable<FlowInstance> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<LocalDateTime> scheduleTriggerTime = column("schedule_trigger_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> actualTriggerTime = column("actual_trigger_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> finishedTime = column("finished_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> instanceId = column("instance_id", JDBCType.BIGINT);

        public final SqlColumn<Long> flowId = column("flow_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> state = column("state", JDBCType.INTEGER);

        public final SqlColumn<Integer> runningTimes = column("running_times", JDBCType.INTEGER);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public FlowInstance() {
            super("flow_instance", FlowInstance::new);
        }
    }
}