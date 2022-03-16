package org.x.flower.flow.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.*;
import org.x.flower.flow.entity.FlowInstanceDo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.x.flower.flow.mapper.FlowInstanceDynamicSqlSupport.*;

@Mapper
public interface FlowInstanceMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<FlowInstanceDo>, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, scheduleTriggerTime, actualTriggerTime, finishedTime, instanceId, flowId, state, runningTimes, createTime, updateTime);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="FlowInstanceResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="schedule_trigger_time", property="scheduleTriggerTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="actual_trigger_time", property="actualTriggerTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="finished_time", property="finishedTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="instance_id", property="instanceId", jdbcType=JdbcType.BIGINT),
        @Result(column="flow_id", property="flowId", jdbcType=JdbcType.BIGINT),
        @Result(column="state", property="state", jdbcType=JdbcType.INTEGER),
        @Result(column="running_times", property="runningTimes", jdbcType=JdbcType.INTEGER),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<FlowInstanceDo> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("FlowInstanceResult")
    Optional<FlowInstanceDo> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, flowInstance, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, flowInstance, completer);
    }

    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(FlowInstanceDo row) {
        return MyBatis3Utils.insert(this::insert, row, flowInstance, c ->
            c.map(id).toProperty("id")
            .map(scheduleTriggerTime).toProperty("scheduleTriggerTime")
            .map(actualTriggerTime).toProperty("actualTriggerTime")
            .map(finishedTime).toProperty("finishedTime")
            .map(instanceId).toProperty("instanceId")
            .map(flowId).toProperty("flowId")
            .map(state).toProperty("state")
            .map(runningTimes).toProperty("runningTimes")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    default int insertMultiple(Collection<FlowInstanceDo> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, flowInstance, c ->
            c.map(id).toProperty("id")
            .map(scheduleTriggerTime).toProperty("scheduleTriggerTime")
            .map(actualTriggerTime).toProperty("actualTriggerTime")
            .map(finishedTime).toProperty("finishedTime")
            .map(instanceId).toProperty("instanceId")
            .map(flowId).toProperty("flowId")
            .map(state).toProperty("state")
            .map(runningTimes).toProperty("runningTimes")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
        );
    }

    default int insertSelective(FlowInstanceDo row) {
        return MyBatis3Utils.insert(this::insert, row, flowInstance, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(scheduleTriggerTime).toPropertyWhenPresent("scheduleTriggerTime", row::getScheduleTriggerTime)
            .map(actualTriggerTime).toPropertyWhenPresent("actualTriggerTime", row::getActualTriggerTime)
            .map(finishedTime).toPropertyWhenPresent("finishedTime", row::getFinishedTime)
            .map(instanceId).toPropertyWhenPresent("instanceId", row::getInstanceId)
            .map(flowId).toPropertyWhenPresent("flowId", row::getFlowId)
            .map(state).toPropertyWhenPresent("state", row::getState)
            .map(runningTimes).toPropertyWhenPresent("runningTimes", row::getRunningTimes)
            .map(createTime).toPropertyWhenPresent("createTime", row::getCreateTime)
            .map(updateTime).toPropertyWhenPresent("updateTime", row::getUpdateTime)
        );
    }

    default Optional<FlowInstanceDo> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, flowInstance, completer);
    }

    default List<FlowInstanceDo> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, flowInstance, completer);
    }

    default List<FlowInstanceDo> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, flowInstance, completer);
    }

    default Optional<FlowInstanceDo> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, flowInstance, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(FlowInstanceDo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(scheduleTriggerTime).equalTo(row::getScheduleTriggerTime)
                .set(actualTriggerTime).equalTo(row::getActualTriggerTime)
                .set(finishedTime).equalTo(row::getFinishedTime)
                .set(instanceId).equalTo(row::getInstanceId)
                .set(flowId).equalTo(row::getFlowId)
                .set(state).equalTo(row::getState)
                .set(runningTimes).equalTo(row::getRunningTimes)
                .set(createTime).equalTo(row::getCreateTime)
                .set(updateTime).equalTo(row::getUpdateTime);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(FlowInstanceDo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(scheduleTriggerTime).equalToWhenPresent(row::getScheduleTriggerTime)
                .set(actualTriggerTime).equalToWhenPresent(row::getActualTriggerTime)
                .set(finishedTime).equalToWhenPresent(row::getFinishedTime)
                .set(instanceId).equalToWhenPresent(row::getInstanceId)
                .set(flowId).equalToWhenPresent(row::getFlowId)
                .set(state).equalToWhenPresent(row::getState)
                .set(runningTimes).equalToWhenPresent(row::getRunningTimes)
                .set(createTime).equalToWhenPresent(row::getCreateTime)
                .set(updateTime).equalToWhenPresent(row::getUpdateTime);
    }

    default int updateByPrimaryKey(FlowInstanceDo row) {
        return update(c ->
            c.set(scheduleTriggerTime).equalTo(row::getScheduleTriggerTime)
            .set(actualTriggerTime).equalTo(row::getActualTriggerTime)
            .set(finishedTime).equalTo(row::getFinishedTime)
            .set(instanceId).equalTo(row::getInstanceId)
            .set(flowId).equalTo(row::getFlowId)
            .set(state).equalTo(row::getState)
            .set(runningTimes).equalTo(row::getRunningTimes)
            .set(createTime).equalTo(row::getCreateTime)
            .set(updateTime).equalTo(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(FlowInstanceDo row) {
        return update(c ->
            c.set(scheduleTriggerTime).equalToWhenPresent(row::getScheduleTriggerTime)
            .set(actualTriggerTime).equalToWhenPresent(row::getActualTriggerTime)
            .set(finishedTime).equalToWhenPresent(row::getFinishedTime)
            .set(instanceId).equalToWhenPresent(row::getInstanceId)
            .set(flowId).equalToWhenPresent(row::getFlowId)
            .set(state).equalToWhenPresent(row::getState)
            .set(runningTimes).equalToWhenPresent(row::getRunningTimes)
            .set(createTime).equalToWhenPresent(row::getCreateTime)
            .set(updateTime).equalToWhenPresent(row::getUpdateTime)
            .where(id, isEqualTo(row::getId))
        );
    }
}