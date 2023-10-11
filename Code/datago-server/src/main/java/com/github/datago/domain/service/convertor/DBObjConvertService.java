package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.aggregate.DataBase;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 数据库对象转换服务
 */
@Component
public class DBObjConvertService {

    @Resource
    private IDBObjConvertor<Schema, DataBase> schemaConvert;

    @Resource
    private IDBObjConvertor<Table, Schema> tableConvert;

    public void convert(Collection<Schema> schemas, DataBase tDatabase) {
        if (!CollectionUtils.isEmpty(schemas)) {
            for (Schema schema : schemas) {
                Schema convertSchema = schemaConvert.convert(schema, tDatabase);

                convert(schema.tables(), convertSchema);
            }
        }
    }

    /**
     * 数据库对象转换
     * @param tables 待转换的表对象
     * @param tSchema 目标库Schema
     */
    public void convert(Collection<Table> tables, Schema tSchema) {
        if (!CollectionUtils.isEmpty(tables)) {
            tables.forEach(table -> {
                Table convertTable = tableConvert.convert(table, tSchema);
            });
        }
    }
}