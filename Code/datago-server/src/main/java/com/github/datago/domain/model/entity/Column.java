package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Column extends DBObject {

    private Boolean nullable;

    private String dataType;

    public Column(String name) {
        super(name);
    }

    public Table getTable() {
        return (Table) getParent();
    }

    @Override
    public DataBase dataBase() {
        return getTable().dataBase();
    }

    public Column(Column column) {
        super(column.getName());

        this.nullable = column.nullable;
        this.dataType = column.dataType;
    }
}
