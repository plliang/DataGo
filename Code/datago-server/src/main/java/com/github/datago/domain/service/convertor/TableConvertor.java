package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

/**
 * 表对象转换
 */
@Component
public class TableConvertor implements IDBObjConvertor<Table> {

    /**
     * 列转换器
     */
    private final IDBObjConvertor<Column> columnConvertor;

    public TableConvertor(IDBObjConvertor<Column> columnConvertor) {
        this.columnConvertor = columnConvertor;
    }


    @Override
    public Table convert(Table src, DataBaseMapping mapping) {
        String mappingSchemaKey = mapping.getMappings().get(src.getParent().key());
        Schema targetSchema = mapping.getTargetDataBase().get(mappingSchemaKey);

        Table convertTable = new Table(targetSchema.dataBase().getName(src.getName()));
        targetSchema.putTable(convertTable);
        mapping.addMapping(src, convertTable);

        for (Column column : src.columns()) {
            Column tColumn = columnConvertor.convert(column, mapping);
            convertTable.putColumn(tColumn);
        }
        return convertTable;
    }
}
