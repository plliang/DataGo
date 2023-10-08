package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 数据库查询命令
 */
public interface IDBQueryCommand {

    /**
     * 数据库对象查询指令
     * @param dataSource 数据库连接池
     * @param dataBase 数据库聚合对象
     */
    public void query(DataSource dataSource, DataBase dataBase) throws SQLException;
}
