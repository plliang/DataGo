package com.github.datago.domain.service.task;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.model.task.TaskContext;
import com.github.datago.domain.model.task.TaskNode;
import com.github.datago.domain.service.ddl.DDLService;

import java.util.concurrent.RecursiveAction;

/**
 * 结构迁移任务
 */
public class StructTaskMigrateAction extends RecursiveAction {

    public StructTaskMigrateAction(TaskNode taskNode, DDLService ddlService) {
        this.taskNode = taskNode;
        this.ddlService = ddlService;
    }

    /**
     * 任务节点
     */
    private final TaskNode taskNode;

    private DDLService ddlService;

    @Override
    protected void compute() {
        // 获取迁移对象的key
        String objKey = taskNode.getKey();

        // 获取目标库映射对象的key
        TaskContext taskContext = taskNode.getTaskContext();
        DataBaseMapping mapping = taskContext.getMapping();
        String targetKey = mapping.getMappings().get(objKey);

        // 获取生成的目标库对象
        DataBase targetDataBase = mapping.getTargetDataBase();
        DBObject dbObject = targetDataBase.get(targetKey);

        if (dbObject != null) {
            String sql = "";
            if (dbObject instanceof Schema) {
                sql = ddlService.generate("createSchema", (Schema) dbObject);
            } else if (dbObject instanceof Table) {
                sql = ddlService.generate("createTable", (Table) dbObject);
            }

            System.out.println("创建SQL: " + sql);
        }
    }
}
