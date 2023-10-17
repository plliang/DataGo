package com.github.datago.domain.model.convertor;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DataBaseMapping {

    private Map<String, String> mappings = new HashMap<>();

    private DataBase srcDataBase;

    private DataBase targetDataBase;

    public void addMapping(DBObject srcObj, DBObject targetObj) {
        mappings.put(srcObj.key(), targetObj.key());
    }


}
