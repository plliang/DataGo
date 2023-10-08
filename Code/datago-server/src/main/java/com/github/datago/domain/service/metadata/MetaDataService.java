package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 元数据查询服务，根据数据库类型选择
 */
@Component
public class MetaDataService {

    @Resource
    private CommandManager commandManager;

    /**
     * 更新DataBase对象，重数据库中重新查询覆盖当前库中的对象
     * @param dataBase
     * @return
     */
    public DataBase refresh(DataBase dataBase) {

        return dataBase;
    }

    public void doQuery(String type, DataBase dataBase) throws SQLException {
        Optional<IDBQueryCommand> optional = commandManager.get(type);
        if (optional.isPresent()) {
            optional.get().query(null, dataBase);
        }
    }

    public void findSchemas(DataBase dataBase) throws SQLException {
        doQuery("schema", dataBase);
    }

    public void findTables(DataBase dataBase) throws SQLException {
        doQuery("tables", dataBase);
    }

    public void findColumns(DataBase dataBase) throws SQLException {
        doQuery("columns", dataBase);
    }
}
