package com.github.datago.domain.model.entity;

import com.github.datago.domain.model.DBObject;
import lombok.Setter;

@Setter
public class Column extends DBObject {

    private Boolean nullable;

    private String dataType;

    public Column(String name) {
        super(name);
    }
}
