package com.github.datago.domain.service.task;

import com.github.datago.DataGoApplication;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.DBConnect;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.model.task.TaskContext;
import com.github.datago.domain.model.task.TaskNode;
import com.github.datago.domain.model.task.TaskStatus;
import com.github.datago.domain.model.task.TaskType;
import com.github.datago.domain.service.convertor.DBObjConvertService;
import com.github.datago.domain.service.ddl.DDLService;
import com.github.datago.domain.service.metadata.MetaDataService;
import com.github.datago.infrastructure.connect.DatasourceFactory;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DataGoApplication.class)
class TaskManagerServiceTest {


    @Resource
    private MetaDataService metaDataService;

    @Resource
    private DBObjConvertService dbObjConvertService;

    @Resource
    private DDLService ddlService;

    @Resource
    private TaskManagerService taskManagerService;

    @Test
    void submit() throws SQLException {
        DataBaseMapping mapping = new DataBaseMapping();

        DataBase dataBase = new DataBase();
        mapping.setSrcDataBase(dataBase);
        DBConnect dbConnect = dbConnect();

        DataSource dataSource = DatasourceFactory.newInstance(dbConnect);
        metaDataService.findSchemas(dataBase, dataSource, null);
        metaDataService.findTables(dataBase, dataSource, null);
        metaDataService.findColumns(dataBase, dataSource, null);

        DataBase tDatabase = new DataBase();
        mapping.setTargetDataBase(tDatabase);
        tDatabase.setSensitiveMode(CaseSensitiveMode.UPPER);

        dbObjConvertService.convert(dataBase.schemas(), mapping);

        Schema tSchema = tDatabase.getSchema("flowable_sample".toUpperCase());
        String createSchema = ddlService.generate("createSchema", tSchema);

        TaskContext taskContext = new TaskContext();
        taskContext.setTaskType(TaskType.struct);
        taskContext.setMapping(mapping);
        taskContext.setStatus(TaskStatus.NEW);

        List<TaskNode> taskNodeList = new ArrayList<>();
        taskContext.setTaskNodes(taskNodeList);

        for (Schema schema : mapping.getSrcDataBase().getSchemas().values()) {
            for (Table table : schema.getTableMap().values()) {
                TaskNode taskNode = new TaskNode();

                taskNode.setTaskType(TaskType.struct);
                taskNode.setTaskContext(taskContext);
                taskNode.setKey(table.key());

                taskNodeList.add(taskNode);
            }
        }


        taskManagerService.submit(taskContext);
    }

    @Test
    void shutdown() {
    }

    @Test
    void waitTerminal() {
    }

    private DBConnect dbConnect() {
        DBConnect dbConnect = new DBConnect();
        dbConnect.setUrl("jdbc:mysql://106.13.199.222");
        dbConnect.setUsername("root");
        dbConnect.setPassword("mysqlPassword");

        return dbConnect;
    }
}