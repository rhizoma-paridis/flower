package org.x.flower.flow.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.time.LocalDateTime;

public final class FlowDynamicSqlSupport {
    public static final Flow flow = new Flow();

    public static final SqlColumn<Long> id = flow.id;

    public static final SqlColumn<String> flowName = flow.flowName;

    public static final SqlColumn<Byte> isDisable = flow.isDisable;

    public static final SqlColumn<LocalDateTime> nextTriggerTime = flow.nextTriggerTime;

    public static final SqlColumn<LocalDateTime> startTime = flow.startTime;

    public static final SqlColumn<String> trigger = flow.trigger;

    public static final SqlColumn<Integer> triggerType = flow.triggerType;

    public static final SqlColumn<Integer> fillBackSequence = flow.fillBackSequence;

    public static final SqlColumn<Integer> flowType = flow.flowType;

    public static final SqlColumn<String> desc = flow.desc;

    public static final SqlColumn<LocalDateTime> createTime = flow.createTime;

    public static final SqlColumn<LocalDateTime> updateTime = flow.updateTime;

    public static final SqlColumn<String> dag = flow.dag;

    public static final class Flow extends AliasableSqlTable<Flow> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> flowName = column("flow_name", JDBCType.VARCHAR);

        public final SqlColumn<Byte> isDisable = column("is_disable", JDBCType.TINYINT);

        public final SqlColumn<LocalDateTime> nextTriggerTime = column("next_trigger_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> startTime = column("start_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> trigger = column("trigger", JDBCType.VARCHAR);

        public final SqlColumn<Integer> triggerType = column("trigger_type", JDBCType.INTEGER);

        public final SqlColumn<Integer> fillBackSequence = column("fill_back_sequence", JDBCType.INTEGER);

        public final SqlColumn<Integer> flowType = column("flow_type", JDBCType.INTEGER);

        public final SqlColumn<String> desc = column("desc", JDBCType.VARCHAR);

        public final SqlColumn<LocalDateTime> createTime = column("create_time", JDBCType.TIMESTAMP);

        public final SqlColumn<LocalDateTime> updateTime = column("update_time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> dag = column("dag", JDBCType.LONGVARCHAR);

        public Flow() {
            super("flow", Flow::new);
        }
    }
}