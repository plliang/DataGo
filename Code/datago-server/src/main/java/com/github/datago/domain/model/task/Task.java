package com.github.datago.domain.model.task;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Task {

    /**
     * 任务状态
     */
    private TaskStatus taskStatus = TaskStatus.NEW;

    /**
     * 任务上下文
     */
    private TaskContext taskContext;

    /**
     * 依赖的任务，in全部完成之后当前任务才可以执行
     */
    private List<Task> in;

    /**
     * 当前任务完成后提交执行out任务
     */
    private List<Task> out;

}
