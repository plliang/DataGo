package com.github.datago.domain.model.aggregate;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.entity.DBConnect;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 数据库对象聚合根
 */
@Getter
@Setter
public class DataBase extends DBObject {

    private String quote = "\"";

    private DBConnect dbConnect;

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

    public String getName(String name) {
        switch (sensitiveMode) {
            case UPPER:
                return name.toUpperCase();
            case LOWER:
                return name.toLowerCase();
            default:
                return name;
        }
    }

    public void putSchema(Schema schema) {
        if (CollectionUtils.isEmpty(schemas)) {
            schemas = new HashMap<>();
        }
        schemas.put(schema.getName(), schema);
        schema.setParent(this);
    }

    public Collection<Schema> schemas() {
        if (!CollectionUtils.isEmpty(schemas)) {
            return schemas.values();
        }
        return List.of();
    }

    @Override
    public DataBase dataBase() {
        return this;
    }

    public Schema get(String key) {
        if (!CollectionUtils.isEmpty(this.schemas)) {
            return schemas.getOrDefault(key, null);
        }
        return null;
    }

    public String getDBName(String name) {
        return quote + name + quote;
    }
}
