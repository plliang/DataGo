package com.github.datago.domain.service.metadata;

import com.github.datago.DataGoApplication;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.DBConnect;
import com.github.datago.infrastructure.connect.DatasourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = DataGoApplication.class)
class MetaDataServiceTest {

    @Resource
    private MetaDataService metaDataService;

    @Test
    void findSchemas() throws SQLException {
        DataBase dataBase = new DataBase();
        DBConnect dbConnect = dbConnect();

        DataSource dataSource = DatasourceFactory.newInstance(dbConnect);
        metaDataService.findSchemas(dataBase, dataSource, null);
        metaDataService.findTables(dataBase, dataSource, List.of("flowable_sample"));
        metaDataService.findColumns(dataBase, dataSource, null);

        System.out.println();
    }

    @Test
    void findTables() {
    }

    @Test
    void findColumns() {
    }

    private DBConnect dbConnect() {
        DBConnect dbConnect = new DBConnect();
        dbConnect.setUrl("jdbc:mysql://106.13.199.222");
        dbConnect.setUsername("root");
        dbConnect.setPassword("mysqlPassword");

        return dbConnect;
    }
}