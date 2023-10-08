package com.github.datago.domain.model.aggregate;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 数据库对象聚合根
 */
@Getter
@Setter
public class DataBase extends DBObject {

    /**
     * 大小写敏感模式
     */
    private CaseSensitiveMode sensitiveMode;

    private Map<String, Schema> schemas;

    public DataBase() {
        super(null);
    }


    public Schema getSchema(String name) {
        return findDBObj(schemas, name);
    }
}
