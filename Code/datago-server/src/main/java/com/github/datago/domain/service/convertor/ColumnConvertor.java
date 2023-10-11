package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.entity.Column;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;

/**
 * 列对象转换
 */
@Component
public class ColumnConvertor implements IDBObjConvertor<Column, Table>  {

    protected void typeSet(Column src, Column target) {
        target.setDataType(src.getDataType());
    }

    protected void defaultValueSet(Column src, Column target) {

    }

    @Override
    public Column convert(Column src, Table table) {

        Column convertColumn = new Column(src);
        convertColumn.setName(table.schema().dataBase().getName(src.getName()));

        typeSet(src, convertColumn);
        defaultValueSet(src, convertColumn);

        return convertColumn;
    }
}
