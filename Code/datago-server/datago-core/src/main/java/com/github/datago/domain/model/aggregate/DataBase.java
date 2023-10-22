package com.github.datago.domain.model.aggregate;

import com.github.datago.domain.model.entity.DBObject;
import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.vo.CaseSensitiveMode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库对象聚合根
 */
@Getter
@Setter
public class DataBase extends DBObject {

    private String dbType;

    private String version;

    private String quote = "\"";

    //private DBConnect dbConnect;

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

    public DBObject get(String key) {
        DBObject ret = null;
        if (!CollectionUtils.isEmpty(this.schemas)) {
            ret = get(schemas, key);

            if (ret == null) {
                for (Schema schema : schemas.values()) {
                    ret = get(schema.getSequenceMap(), key);

                    if (ret == null) {
                        ret = get(schema.getTableMap(), key);
                    }

                    if (ret != null) {
                        return ret;
                    }
                }
            }
        }
        return ret;
    }

    private DBObject get(Map<String, ? extends DBObject> objMap, String key) {
        if (!CollectionUtils.isEmpty(objMap)) {
            for (DBObject dbObject : objMap.values()) {
                if (dbObject.key().equals(key)) {
                    return dbObject;
                }
            }
        }
        return null;
    }

    public String getDBName(String name) {
        return quote + name + quote;
    }
}
