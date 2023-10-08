package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractQueryCommand implements IDBQueryCommand {

    private DataSource dataSource;

    public AbstractQueryCommand(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * SQL查询处理公共方法
     * @param sql
     * @param t
     * @param function2
     * @throws Exception
     */
    protected void doQuery(String sql, DataBase dataBase,
                               Function2<PreparedStatement, DataBase> function2) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            function2.accept(preparedStatement, dataBase);
        }
    }

    protected interface Function2<T1, T2 extends Object>  {

        public void accept(T1 t1, T2 t2) throws SQLException;

    }
}
