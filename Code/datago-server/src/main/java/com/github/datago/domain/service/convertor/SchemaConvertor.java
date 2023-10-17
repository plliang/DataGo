package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

@Component
public class SchemaConvertor implements IDBObjConvertor<Schema> {

    private final IDBObjConvertor<Table> tableConvertor;

    public SchemaConvertor(IDBObjConvertor<Table> tableConvertor) {
        this.tableConvertor = tableConvertor;
    }

    @Override
    public Schema convert(Schema src, DataBaseMapping mapping) {
        DataBase targetDataBase = mapping.getTargetDataBase();
        Schema convertSchema = new Schema(targetDataBase.getName(src.getName()));
        targetDataBase.putSchema(convertSchema);
        mapping.addMapping(src, convertSchema);

        for (Table table : src.tables()) {
            tableConvertor.convert(table, mapping);
        }
        return convertSchema;
    }
}
