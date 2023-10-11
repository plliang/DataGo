package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
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
    private DataBase refresh(DataBase dataBase) {

        return dataBase;
    }

    public void doQuery(String type, DataBase dataBase, DataSource dataSource) throws SQLException {
        Optional<IDBQueryCommand> optional = commandManager.get(type);
        if (optional.isPresent()) {
            IDBQueryCommand queryCommand = optional.get();
            queryCommand.setDataSource(dataSource);
            queryCommand.query(dataSource, dataBase);
        }
    }

    public void findSchemas(DataBase dataBase, DataSource dataSource) throws SQLException {
        doQuery("schemas", dataBase, dataSource);
    }

    public void findTables(DataBase dataBase, DataSource dataSource) throws SQLException {
        doQuery("tables", dataBase, dataSource);
    }

    public void findColumns(DataBase dataBase, DataSource dataSource) throws SQLException {
        doQuery("columns", dataBase, dataSource);
    }
}
