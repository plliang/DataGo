package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;

import java.util.HashMap;
import java.util.Map;

public class Table extends DBObject {

    private Map<String, Column> columnMap = new HashMap<>();

    public Table(String name) {
        super(name);
    }

    public void putColumn(Column column) {
        columnMap.put(column.getName(), column);
    }
}
