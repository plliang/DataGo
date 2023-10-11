package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

@Component
public class SchemaConvertor implements IDBObjConvertor<Schema, DataBase> {

    private final IDBObjConvertor<Table, Schema> tableConvertor;

    public SchemaConvertor(IDBObjConvertor<Table, Schema> tableConvertor) {
        this.tableConvertor = tableConvertor;
    }

    @Override
    public Schema convert(Schema src, DataBase dataBase) {
        Schema convertSchema = new Schema(dataBase.getName(src.getName()));
        dataBase.putSchema(convertSchema);

        for (Table table : src.tables()) {
            tableConvertor.convert(table, convertSchema);
        }
        return convertSchema;
    }
}
