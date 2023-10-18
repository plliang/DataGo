package com.github.datago.domain.model.task;

import com.github.datago.domain.model.convertor.DataBaseMapping;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.List;

@Setter
@Getter
public class TaskContext {

    private DataBaseMapping mapping;

    /**
     * 源库数据源
     */
    private DataSource srcDataSource;

    /**
     * 目标库数据源
     */
    private DataSource targetDataSource;

    /**
     * 任务类型
     */
    private TaskType taskType;

    /**
     * 任务状态
     */
    private TaskStatus status;

    /**
     * 任务集合
     */
    private List<TaskNode> taskNodes;
}
