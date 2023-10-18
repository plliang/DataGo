package com.github.datago.domain.model.task;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
public class TaskNode {

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
    private List<TaskNode> in;

    /**
     * 当前任务完成后提交执行out任务
     */
    private List<TaskNode> out;

    /**
     * 需要迁移的对象key
     */
    private String key;

    /**
     * 任务类型
     */
    private TaskType taskType;

    /**
     * 是否已经结束
     * @return 结束或者取消都返回true
     */
    public boolean end() {
        return TaskStatus.END.equals(taskStatus) || TaskStatus.CANCEL.equals(taskStatus);
    }

    public boolean inEnd() {
        if (!CollectionUtils.isEmpty(in)) {
            for (TaskNode taskNode : in) {
                if (!taskNode.end()) {
                    return false;
                }
            }
        }
        return true;
    }
}
