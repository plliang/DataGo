package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class Table extends DBObject {

    private Map<String, Column> columnMap = new HashMap<>();

    public Table(String name) {
        super(name);
    }

    public void putColumn(Column column) {
        columnMap.put(column.getName(), column);
        column.setParent(this);
    }

    public Collection<Column> columns() {
        if (!CollectionUtils.isEmpty(columnMap)) {
            return columnMap.values();
        }
        return Collections.emptyList();
    }

    public Schema schema() {
        return (Schema) getParent();
    }

    @Override
    public DataBase dataBase() {
        return schema().dataBase();
    }

    @Override
    public String key() {
        return String.join("_", getParent().key(), getName());
    }
}
