package com.github.datago.domain.service.ddl;

import com.github.datago.DataGoApplication;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.DBConnect;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.service.convertor.DBObjConvertService;
import com.github.datago.domain.service.metadata.MetaDataService;
import com.github.datago.infrastructure.connect.DatasourceFactory;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DataGoApplication.class)
class DDLServiceTest {

    @Resource
    private MetaDataService metaDataService;

    @Resource
    private DBObjConvertService dbObjConvertService;

    @Resource
    private DDLService ddlService;

    @Test
    void generate() throws SQLException {
        DataBaseMapping mapping = new DataBaseMapping();

        DataBase dataBase = new DataBase();
        mapping.setSrcDataBase(dataBase);
        DBConnect dbConnect = dbConnect();

        DataSource dataSource = DatasourceFactory.newInstance(dbConnect);
        metaDataService.findSchemas(dataBase, dataSource, null);
        metaDataService.findTables(dataBase, dataSource, List.of("flowable_sample"));
        metaDataService.findColumns(dataBase, dataSource, null);

        DataBase tDatabase = new DataBase();
        mapping.setTargetDataBase(tDatabase);
        tDatabase.setSensitiveMode(CaseSensitiveMode.UPPER);

        dbObjConvertService.convert(dataBase.schemas(), mapping);

        Schema tSchema = tDatabase.getSchema("flowable_sample".toUpperCase());
        String createSchema = ddlService.generate("createSchema", tSchema);
        System.out.println("schema创建: " + createSchema);

        for (Table table : tSchema.tables()) {
            String createTable = ddlService.generate("createTable", table);
            System.out.println("表创建：" + createTable);
        }
    }

    @Test
    void testGenerate() {
    }

    private DBConnect dbConnect() {
        DBConnect dbConnect = new DBConnect();
        dbConnect.setUrl("jdbc:mysql://106.13.199.222");
        dbConnect.setUsername("root");
        dbConnect.setPassword("mysqlPassword");

        return dbConnect;
    }
}