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
import org.x.flower.flow.entity.FlowDo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.x.flower.flow.mapper.FlowDynamicSqlSupport.*;

@Mapper
public interface FlowMapper extends CommonCountMapper, CommonDeleteMapper, CommonInsertMapper<FlowDo>, CommonUpdateMapper {
    BasicColumn[] selectList = BasicColumn.columnList(id, flowName, isDisable, nextTriggerTime, startTime, trigger, triggerType, fillBackSequence, flowType, desc, createTime, updateTime, dag);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="FlowResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="flow_name", property="flowName", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_disable", property="isDisable", jdbcType=JdbcType.TINYINT),
        @Result(column="next_trigger_time", property="nextTriggerTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="start_time", property="startTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="trigger", property="trigger", jdbcType=JdbcType.VARCHAR),
        @Result(column="trigger_type", property="triggerType", jdbcType=JdbcType.INTEGER),
        @Result(column="fill_back_sequence", property="fillBackSequence", jdbcType=JdbcType.INTEGER),
        @Result(column="flow_type", property="flowType", jdbcType=JdbcType.INTEGER),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="dag", property="dag", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<FlowDo> selectMany(SelectStatementProvider selectStatement);

    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("FlowResult")
    Optional<FlowDo> selectOne(SelectStatementProvider selectStatement);

    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, flow, completer);
    }

    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, flow, completer);
    }

    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    default int insert(FlowDo row) {
        return MyBatis3Utils.insert(this::insert, row, flow, c ->
            c.map(id).toProperty("id")
            .map(flowName).toProperty("flowName")
            .map(isDisable).toProperty("isDisable")
            .map(nextTriggerTime).toProperty("nextTriggerTime")
            .map(startTime).toProperty("startTime")
            .map(trigger).toProperty("trigger")
            .map(triggerType).toProperty("triggerType")
            .map(fillBackSequence).toProperty("fillBackSequence")
            .map(flowType).toProperty("flowType")
            .map(desc).toProperty("desc")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
            .map(dag).toProperty("dag")
        );
    }

    default int insertMultiple(Collection<FlowDo> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, flow, c ->
            c.map(id).toProperty("id")
            .map(flowName).toProperty("flowName")
            .map(isDisable).toProperty("isDisable")
            .map(nextTriggerTime).toProperty("nextTriggerTime")
            .map(startTime).toProperty("startTime")
            .map(trigger).toProperty("trigger")
            .map(triggerType).toProperty("triggerType")
            .map(fillBackSequence).toProperty("fillBackSequence")
            .map(flowType).toProperty("flowType")
            .map(desc).toProperty("desc")
            .map(createTime).toProperty("createTime")
            .map(updateTime).toProperty("updateTime")
            .map(dag).toProperty("dag")
        );
    }

    default int insertSelective(FlowDo row) {
        return MyBatis3Utils.insert(this::insert, row, flow, c ->
            c.map(id).toPropertyWhenPresent("id", row::getId)
            .map(flowName).toPropertyWhenPresent("flowName", row::getFlowName)
            .map(isDisable).toPropertyWhenPresent("isDisable", row::getIsDisable)
            .map(nextTriggerTime).toPropertyWhenPresent("nextTriggerTime", row::getNextTriggerTime)
            .map(startTime).toPropertyWhenPresent("startTime", row::getStartTime)
            .map(trigger).toPropertyWhenPresent("trigger", row::getTrigger)
            .map(triggerType).toPropertyWhenPresent("triggerType", row::getTriggerType)
            .map(fillBackSequence).toPropertyWhenPresent("fillBackSequence", row::getFillBackSequence)
            .map(flowType).toPropertyWhenPresent("flowType", row::getFlowType)
            .map(desc).toPropertyWhenPresent("desc", row::getDesc)
            .map(createTime).toPropertyWhenPresent("createTime", row::getCreateTime)
            .map(updateTime).toPropertyWhenPresent("updateTime", row::getUpdateTime)
            .map(dag).toPropertyWhenPresent("dag", row::getDag)
        );
    }

    default Optional<FlowDo> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, flow, completer);
    }

    default List<FlowDo> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, flow, completer);
    }

    default List<FlowDo> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, flow, completer);
    }

    default Optional<FlowDo> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, flow, completer);
    }

    static UpdateDSL<UpdateModel> updateAllColumns(FlowDo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(row::getId)
                .set(flowName).equalTo(row::getFlowName)
                .set(isDisable).equalTo(row::getIsDisable)
                .set(nextTriggerTime).equalTo(row::getNextTriggerTime)
                .set(startTime).equalTo(row::getStartTime)
                .set(trigger).equalTo(row::getTrigger)
                .set(triggerType).equalTo(row::getTriggerType)
                .set(fillBackSequence).equalTo(row::getFillBackSequence)
                .set(flowType).equalTo(row::getFlowType)
                .set(desc).equalTo(row::getDesc)
                .set(createTime).equalTo(row::getCreateTime)
                .set(updateTime).equalTo(row::getUpdateTime)
                .set(dag).equalTo(row::getDag);
    }

    static UpdateDSL<UpdateModel> updateSelectiveColumns(FlowDo row, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(row::getId)
                .set(flowName).equalToWhenPresent(row::getFlowName)
                .set(isDisable).equalToWhenPresent(row::getIsDisable)
                .set(nextTriggerTime).equalToWhenPresent(row::getNextTriggerTime)
                .set(startTime).equalToWhenPresent(row::getStartTime)
                .set(trigger).equalToWhenPresent(row::getTrigger)
                .set(triggerType).equalToWhenPresent(row::getTriggerType)
                .set(fillBackSequence).equalToWhenPresent(row::getFillBackSequence)
                .set(flowType).equalToWhenPresent(row::getFlowType)
                .set(desc).equalToWhenPresent(row::getDesc)
                .set(createTime).equalToWhenPresent(row::getCreateTime)
                .set(updateTime).equalToWhenPresent(row::getUpdateTime)
                .set(dag).equalToWhenPresent(row::getDag);
    }

    default int updateByPrimaryKey(FlowDo row) {
        return update(c ->
            c.set(flowName).equalTo(row::getFlowName)
            .set(isDisable).equalTo(row::getIsDisable)
            .set(nextTriggerTime).equalTo(row::getNextTriggerTime)
            .set(startTime).equalTo(row::getStartTime)
            .set(trigger).equalTo(row::getTrigger)
            .set(triggerType).equalTo(row::getTriggerType)
            .set(fillBackSequence).equalTo(row::getFillBackSequence)
            .set(flowType).equalTo(row::getFlowType)
            .set(desc).equalTo(row::getDesc)
            .set(createTime).equalTo(row::getCreateTime)
            .set(updateTime).equalTo(row::getUpdateTime)
            .set(dag).equalTo(row::getDag)
            .where(id, isEqualTo(row::getId))
        );
    }

    default int updateByPrimaryKeySelective(FlowDo row) {
        return update(c ->
            c.set(flowName).equalToWhenPresent(row::getFlowName)
            .set(isDisable).equalToWhenPresent(row::getIsDisable)
            .set(nextTriggerTime).equalToWhenPresent(row::getNextTriggerTime)
            .set(startTime).equalToWhenPresent(row::getStartTime)
            .set(trigger).equalToWhenPresent(row::getTrigger)
            .set(triggerType).equalToWhenPresent(row::getTriggerType)
            .set(fillBackSequence).equalToWhenPresent(row::getFillBackSequence)
            .set(flowType).equalToWhenPresent(row::getFlowType)
            .set(desc).equalToWhenPresent(row::getDesc)
            .set(createTime).equalToWhenPresent(row::getCreateTime)
            .set(updateTime).equalToWhenPresent(row::getUpdateTime)
            .set(dag).equalToWhenPresent(row::getDag)
            .where(id, isEqualTo(row::getId))
        );
    }
}