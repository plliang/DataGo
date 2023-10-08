package com.github.datago.domain.service.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.service.metadata.AbstractQueryCommand;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TableQueryCommand extends AbstractQueryCommand {

    private static final String MYSQL_TABLE_QUERY_SQL = "select TABLE_SCHEMA, TABLE_NAME  " +
            "from information_schema.TABLES " +
            "where TABLE_TYPE = 'BASE TABLE'";

    public TableQueryCommand(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void query(DataSource dataSource, DataBase dataBase) throws SQLException {
        doQuery(MYSQL_TABLE_QUERY_SQL, dataBase, ((preparedStatement, db) -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    String tableSchema = resultSet.getString("TABLE_SCHEMA");
                    String tableName = resultSet.getString("TABLE_NAME");

                    Optional.of(db.getSchema(tableSchema)).ifPresent(schema -> {
                        Table table = new Table(tableName);
                        schema.putTable(table);
                    });
                }
            }
        }));
    }
}
