package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

/**
 * 列对象转换
 */
@Component
public class ColumnConvertor implements IDBObjConvertor<Column>  {

    protected void typeSet(Column src, Column target) {
        target.setDataType(src.getDataType());
    }

    protected void defaultValueSet(Column src, Column target) {

    }

    @Override
    public Column convert(Column src, DataBaseMapping mapping) {
        Table table = src.getTable();
        DBObject schema = table.getParent();

        String targetSchemaKey = mapping.getMappings().get(schema.key());
        String targetTableKey = mapping.getMappings().get(table.key());

        DataBase targetDataBase = mapping.getTargetDataBase();
        Schema targetSchemaObj = targetDataBase.get(targetSchemaKey);
        Table targetTable = targetSchemaObj.findTable(targetTableKey);

        Column convertColumn = new Column(src);
        convertColumn.setParent(targetTable);
        convertColumn.setName(targetDataBase.getName(src.getName()));

        typeSet(src, convertColumn);
        defaultValueSet(src, convertColumn);

        mapping.addMapping(src, convertColumn);
        targetTable.putColumn(convertColumn);
        return convertColumn;
    }
}
