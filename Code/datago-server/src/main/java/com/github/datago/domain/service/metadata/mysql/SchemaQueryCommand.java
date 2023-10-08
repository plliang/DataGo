package com.github.datago.domain.service.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.service.metadata.AbstractQueryCommand;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SchemaQueryCommand extends AbstractQueryCommand {

    private static String MYSQL_SCHEMA_QUERY_SQL = "select SCHEMA_NAME from information_schema.SCHEMATA";

    public SchemaQueryCommand(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void query(DataSource dataSource, DataBase dataBase) throws SQLException {
        doQuery(MYSQL_SCHEMA_QUERY_SQL,dataBase,  (preparedStatement, db) -> {
            ResultSet resultSet = preparedStatement.executeQuery();

            Map<String, Schema> schemaMap = new HashMap<>();
            while (resultSet.next()) {
                String schemaName = resultSet.getString("SCHEMA_NAME");

                Schema schema = new Schema(schemaName);
                schemaMap.put(schemaName, schema);
            }
            db.setSchemas(schemaMap);
        });
    }
}
