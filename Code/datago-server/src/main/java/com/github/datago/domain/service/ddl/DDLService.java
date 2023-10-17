package com.github.datago.domain.service.ddl;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class DDLService {
    private Map<String, IDDLGenerate<? extends DBObject>> ddlGeneratorMap;

    @PostConstruct
    public void init() {
        ddlGeneratorMap = new HashMap<>();

        ddlGeneratorMap.put("createSchema", new SchemaCreateGenerator());
        ddlGeneratorMap.put("createTable", new TableCreateGenerator());
    }

    public String generate(String type, Schema schema) {
        IDDLGenerate<Schema> generate = (IDDLGenerate<Schema>) ddlGeneratorMap.get(type);
        return generate.generate(schema);
    }

    public String generate(String type, Table table) {
        IDDLGenerate<Table> generate = (IDDLGenerate<Table>) ddlGeneratorMap.get(type);
        return generate.generate(table);
    }
}
