package com.github.datago.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Getter
@Setter
public class DBObject {

    private String name;

    public DBObject(String name) {
        this.name = name;
    }

    protected <T> T findDBObj(Map<String, T> dbObjectMap, String name) {
        if (!CollectionUtils.isEmpty(dbObjectMap)) {
            return dbObjectMap.get(name);
        }
        return null;
    }
}
