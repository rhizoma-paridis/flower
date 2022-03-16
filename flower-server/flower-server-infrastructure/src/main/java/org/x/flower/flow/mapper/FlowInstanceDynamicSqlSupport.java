package org.x.flower.flow.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.util.Date;

public final class FlowInstanceDynamicSqlSupport {
    public static final FlowInstance flowInstance = new FlowInstance();

    public static final SqlColumn<Long> id = flowInstance.id;

    public static final SqlColumn<Date> scheduleTriggerTime = flowInstance.scheduleTriggerTime;

    public static final SqlColumn<Date> actualTriggerTime = flowInstance.actualTriggerTime;

    public static final SqlColumn<Date> finishedTime = flowInstance.finishedTime;

    public static final SqlColumn<Long> instanceId = flowInstance.instanceId;

    public static final SqlColumn<Long> flowId = flowInstance.flowId;

    public static final SqlColumn<Integer> state = flowInstance.state;

    public static final SqlColumn<Integer> runningTimes = flowInstance.runningTimes;

    public static final SqlColumn<Date> createTime = flowInstance.createTime;

    public static final SqlColumn<Date> updateTime = flowInstance.updateTime;

    public static final class FlowInstance extends AliasableSqlTable<FlowInstance> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<Date> scheduleTriggerTime = column("schedule_trigger_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> actualTriggerTime = column("actual_trigger_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> finishedTime = column("finished_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Long> instanceId = column("instance_id", JDBCType.BIGINT);

        public final SqlColumn<Long> flowId = column("flow_id", JDBCType.BIGINT);

        public final SqlColumn<Integer> state = column("state", JDBCType.INTEGER);

        public final SqlColumn<Integer> runningTimes = column("running_times", JDBCType.INTEGER);

        public final SqlColumn<Date> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public FlowInstance() {
            super("flow_instance", FlowInstance::new);
        }
    }
}