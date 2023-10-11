package com.github.datago.domain.service.metadata.mysql;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.domain.service.metadata.AbstractQueryCommand;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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

    @Override
    protected String generateQuerySQL(List<String> limit) {
        if (CollectionUtils.isEmpty(limit)) {
            return MYSQL_TABLE_QUERY_SQL;
        }

        List<String> collect = new ArrayList<>();
        Function<String, Object> mapper = s -> "`" + s + "`";
        for (String string : limit) {
            Object o = mapper.apply(string);
            collect.add(o);
        }
        return MYSQL_TABLE_QUERY_SQL + "WHERE TABLE_SCHEMA IN (" + String.join(",", collect) + ")";
    }
}
