package com.github.datago.domain.service.convertor;

import com.github.datago.DataGoApplication;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.DBConnect;
import com.github.datago.domain.service.metadata.MetaDataService;
import com.github.datago.infrastructure.connect.DatasourceFactory;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest(classes = DataGoApplication.class)
class DBObjConvertServiceTest {

    @Resource
    private MetaDataService metaDataService;

    @Resource
    private DBObjConvertService dbObjConvertService;

    @Test
    void convert() throws SQLException {
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
        System.out.println();
    }

    private DBConnect dbConnect() {
        DBConnect dbConnect = new DBConnect();
        dbConnect.setUrl("jdbc:mysql://106.13.199.222");
        dbConnect.setUsername("root");
        dbConnect.setPassword("mysqlPassword");

        return dbConnect;
    }
}