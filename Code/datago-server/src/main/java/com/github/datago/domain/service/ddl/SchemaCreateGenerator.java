package com.github.datago.domain.service.ddl;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Schema;

public class SchemaCreateGenerator implements IDDLGenerate<Schema> {
    @Override
    public String generate(Schema schema) {
        DataBase dataBase = schema.dataBase();
        return "CREATE SCHEMA " + dataBase.getDBName(schema.getName()) + ";";
    }
}
