package com.github.datago.domain.service.task;

import com.github.datago.domain.model.task.TaskContext;
import com.github.datago.domain.model.task.TaskNode;
import com.github.datago.domain.model.task.TaskType;
import com.github.datago.domain.service.ddl.DDLService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * 任务管理
 */
@Component
public class TaskManagerService {

    private final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    @Resource
    private DDLService ddlService;

    public void submit(TaskContext taskContext) {
        for (TaskNode taskNode : taskContext.getTaskNodes()) {
            if (!taskNode.end() && taskNode.inEnd()) {
                if (TaskType.struct.equals(taskNode.getTaskType())) {
                    StructTaskMigrateAction structTaskMigrateAction = new StructTaskMigrateAction(taskNode, ddlService);
                    FORK_JOIN_POOL.submit(structTaskMigrateAction);
                } else if (TaskType.data.equals(taskNode.getTaskType())){
                    System.out.println("数据迁移");
                }
            }
        }
    }

    public void shutdown() {
        FORK_JOIN_POOL.shutdown();
    }

    public void waitTerminal() {
        while (true) {
            try {
                if (FORK_JOIN_POOL.awaitTermination(1, TimeUnit.MINUTES)) {
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
