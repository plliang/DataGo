package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

/**
 * 表对象转换
 */
@Component
public class TableConvertor implements IDBObjConvertor<Table, Schema> {

    /**
     * 列转换器
     */
    private final IDBObjConvertor<Column, Table> columnConvertor;

    public TableConvertor(IDBObjConvertor<Column, Table> columnConvertor) {
        this.columnConvertor = columnConvertor;
    }


    @Override
    public Table convert(Table src, Schema schema) {

        Table convertTable = new Table(schema.dataBase().getName(src.getName()));
        schema.putTable(convertTable);

        for (Column column : src.columns()) {
            Column tColumn = columnConvertor.convert(column, convertTable);
            convertTable.putColumn(tColumn);
        }
        return convertTable;
    }
}
