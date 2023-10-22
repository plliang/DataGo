package com.github.datago.plugin.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.service.metadata.IMetaDataCommand;

import javax.sql.DataSource;

public class MySQLSchemasQuery implements IMetaDataCommand {
    @Override
    public boolean support(String commandKey, DataBase dataBase) {
        return MySQLType.DB_TYPE.equals(dataBase.getDbType()) && "querySchemas".equals(commandKey);
    }

    private static String MYSQL_SCHEMA_QUERY_SQL = "select SCHEMA_NAME from information_schema.SCHEMATA";

    @Override
    public void doQuery(DataBase dataBase, DataSource dataSource) {
        System.out.println("MySQL Schemas Query Finish!!!");
    }
}
