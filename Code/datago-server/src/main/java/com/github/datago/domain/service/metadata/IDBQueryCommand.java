package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库查询命令
 */
public interface IDBQueryCommand {

    public void setDataSource(DataSource dataSource);

    /**
     * 数据库对象查询指令
     * @param dataSource 数据库连接池
     * @param dataBase 数据库聚合对象
     * @param limit 限制查询对象名称
     */
    public void query(DataSource dataSource, DataBase dataBase, List<String> limit) throws SQLException;
}
