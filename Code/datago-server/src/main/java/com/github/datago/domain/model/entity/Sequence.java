package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;

public class Sequence extends DBObject {
    public Sequence(String name) {
        super(name);
    }

    @Override
    public DataBase dataBase() {
        return null;
    }
}
