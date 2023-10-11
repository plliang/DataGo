package com.github.datago.domain.service.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.service.metadata.AbstractQueryCommand;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@NoArgsConstructor
public class ColumnsQueryCommand extends AbstractQueryCommand {

    private static final String MYSQL_COLUMN_QUERY_SQL = "select TABLE_SCHEMA, " +
            " TABLE_NAME, " +
            " COLUMN_NAME, " +
            " IS_NULLABLE , " +
            " DATA_TYPE , " +
            " COLUMN_TYPE , " +
            " CHARACTER_MAXIMUM_LENGTH , " +
            " CHARACTER_OCTET_LENGTH , " +
            " NUMERIC_PRECISION , " +
            " NUMERIC_SCALE , " +
            " DATETIME_PRECISION , " +
            " EXTRA , " +
            " COLUMN_COMMENT " +
            "from " +
            " information_schema.COLUMNS;";


    public ColumnsQueryCommand(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void query(DataSource dataSource, DataBase dataBase) throws SQLException {
        doQuery(MYSQL_COLUMN_QUERY_SQL, dataBase, (preparedStatement, db) -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    String tableSchema = resultSet.getString("TABLE_SCHEMA");
                    String tableName = resultSet.getString("TABLE_NAME");

                    Schema schema = db.getSchema(tableSchema);
                    if (schema != null) {
                        Table table = schema.getTable(tableName);

                        if (table !=null) {
                            String columnName = resultSet.getString("COLUMN_NAME");
                            Column column = new Column(columnName);

                            String isNullable = resultSet.getString("IS_NULLABLE");
                            column.setNullable("YES".equals(isNullable));

                            column.setDataType(resultSet.getString("DATA_TYPE"));

                            table.putColumn(column);
                        }
                    }
                }
            }
        });
    }
}
