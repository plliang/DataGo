package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import lombok.Getter;
import org.assertj.core.util.Maps;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Getter
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

    public Table findTable(String key) {
        return findByKey(tableMap, key);
    }

    public void putTable(Table table) {
        tableMap.put(table.getName(), table);
        table.setParent(this);
    }

    public DataBase dataBase() {
        return (DataBase) getParent();
    }

    public Collection<Table> tables() {
        if (!CollectionUtils.isEmpty(tableMap)) {
            return tableMap.values();
        }
        return List.of();
    }

    @Override
    public String key() {
        return getName();
    }
}
