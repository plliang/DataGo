package com.github.datago.app.metadata;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.service.metadata.MetaDataService;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class MetaDataAppService {

    private MetaDataService metaDataService;

    public void refreshSchemas(DataBase dataBase, DataSource dataSource) {
        metaDataService.query("querySchemas", dataBase, dataSource);
    }

    public void refreshTables(DataBase dataBase, DataSource dataSource) {
        metaDataService.query("queryTables", dataBase, dataSource);
    }

    public void refreshColumns(DataBase dataBase, DataSource dataSource) {
        metaDataService.query("queryColumns", dataBase, dataSource);
    }
}
