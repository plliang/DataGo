package com.github.datago.domain.service.ddl;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableCreateGenerator implements IDDLGenerator<Table> {
    @Override
    public String generate(Table table) {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");

        DataBase dataBase = table.dataBase();
        Schema schema = table.schema();
        sql.append(dataBase.getDBName(schema.getName()))
                .append(".")
                .append(dataBase.getDBName(table.getName()));

        // Column处理
        columnGenerator(sql, dataBase, table.columns());
        sql.append(";");
        // Comment处理
        return sql.toString();
    }

    protected void columnGenerator(StringBuilder sql, DataBase dataBase, Collection<Column> columns) {
        if (!CollectionUtils.isEmpty(columns)) {
            List<String> columnList = new ArrayList<>(columns.size());

            for (Column column : columns) {
                columnList.add(dataBase.getDBName(column.getName()) + " " + column.getDataType());
            }
            sql.append("(")
                    .append(String.join(",", columnList))
                    .append(")");
        }
    }
}
