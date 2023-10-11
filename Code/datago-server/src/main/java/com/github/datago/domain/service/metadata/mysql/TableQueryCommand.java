package com.github.datago.domain.service.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.service.metadata.AbstractQueryCommand;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
public class TableQueryCommand extends AbstractQueryCommand {

    private static final String MYSQL_TABLE_QUERY_SQL = "select TABLE_SCHEMA, TABLE_NAME  " +
            "from information_schema.TABLES " +
            "where TABLE_TYPE = 'BASE TABLE'";

    public TableQueryCommand(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public void query(DataSource dataSource, DataBase dataBase, List<String> limit) throws SQLException {
        doQuery(generateQuerySQL(limit), dataBase, ((preparedStatement, db) -> {
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

    @Override
    protected String generateQuerySQL(List<String> schemaList) {
        if (CollectionUtils.isEmpty(schemaList)) {
            return MYSQL_TABLE_QUERY_SQL;
        }

        List<String> schemas = schemaList.stream().map(schema -> "'" + schema + "'").collect(Collectors.toList());
        return MYSQL_TABLE_QUERY_SQL + " AND TABLE_SCHEMA IN (" + String.join(",", schemas) + ")";
    }
}
