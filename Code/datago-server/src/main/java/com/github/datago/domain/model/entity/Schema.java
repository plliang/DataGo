package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;

import java.util.HashMap;
import java.util.Map;

public class Schema extends DBObject {

    private Map<String, Sequence> sequenceMap;

    private Map<String, Table> tableMap = new HashMap<>();

    public Schema(String name) {
        super(name);
    }

    public Sequence getSequence(String name) {
        return findDBObj(sequenceMap, name);
    }

    public Table getTable(String name) {
        return findDBObj(tableMap, name);
    }

    public void putTable(Table table) {
        tableMap.putIfAbsent(table.getName(), table);
    }
}
