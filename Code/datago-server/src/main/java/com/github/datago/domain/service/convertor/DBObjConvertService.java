package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.convertor.DataBaseMapping;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Table;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 数据库对象转换服务
 */
@Component
public class DBObjConvertService {

    @Resource
    private IDBObjConvertor<Schema> schemaConvert;

    @Resource
    private IDBObjConvertor<Table> tableConvert;

    public void convert(Collection<Schema> schemas, DataBaseMapping mapping) {
        if (!CollectionUtils.isEmpty(schemas)) {
            for (Schema schema : schemas) {
                schemaConvert.convert(schema, mapping);

                convertTables(schema.tables(), mapping);
            }
        }
    }

    /**
     * 数据库对象转换
     * @param tables 待转换的表对象
     * @param tSchema 目标库Schema
     */
    public void convertTables(Collection<Table> tables, DataBaseMapping mapping) {
        if (!CollectionUtils.isEmpty(tables)) {
            tables.forEach(table -> {
                Table convertTable = tableConvert.convert(table, mapping);
            });
        }
    }
}