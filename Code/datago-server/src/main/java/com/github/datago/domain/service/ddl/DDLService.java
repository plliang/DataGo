package com.github.datago.domain.service.ddl;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DDLService {
    private Map<String, IDDLGenerator<? extends DBObject>> ddlGeneratorMap;

    @PostConstruct
    public void init() {
        ddlGeneratorMap = new HashMap<>();

        ddlGeneratorMap.put("createSchema", new SchemaCreateGenerator());
        ddlGeneratorMap.put("createTable", new TableCreateGenerator());
    }

    public String generate(String type, Schema schema) {
        IDDLGenerator<Schema> generate = (IDDLGenerator<Schema>) ddlGeneratorMap.get(type);
        return generate.generate(schema);
    }

    public String generate(String type, Table table) {
        IDDLGenerator<Table> generate = (IDDLGenerator<Table>) ddlGeneratorMap.get(type);
        return generate.generate(table);
    }
}
