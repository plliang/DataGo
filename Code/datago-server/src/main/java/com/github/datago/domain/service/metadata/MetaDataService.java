package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
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

    public void doQuery(CommandKey key, DataBase dataBase, DataSource dataSource, List<String> limit) throws SQLException {
        Optional<IDBQueryCommand> optional = commandManager.get(key);
        if (optional.isPresent()) {
            IDBQueryCommand queryCommand = optional.get();
            queryCommand.setDataSource(dataSource);
            queryCommand.query(dataSource, dataBase, limit);
        }
    }

    public void findSchemas(DataBase dataBase, DataSource dataSource, List<String> limit) throws SQLException {
        doQuery(CommandKey.schema, dataBase, dataSource, limit);
    }

    public void findTables(DataBase dataBase, DataSource dataSource, List<String> limit) throws SQLException {
        doQuery(CommandKey.table, dataBase, dataSource, limit);
    }

    public void findColumns(DataBase dataBase, DataSource dataSource, List<String> limit) throws SQLException {
        doQuery(CommandKey.column, dataBase, dataSource, limit);
    }
}
