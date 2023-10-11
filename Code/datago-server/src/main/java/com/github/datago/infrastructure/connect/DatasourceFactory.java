package com.github.datago.infrastructure.connect;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.datago.domain.model.entity.DBConnect;

import javax.sql.DataSource;

public class DatasourceFactory {

    public static DataSource newInstance(DBConnect dbConnect) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbConnect.getUrl());
        dataSource.setUsername(dbConnect.getUsername());
        dataSource.setPassword(dbConnect.getPassword());
        dataSource.setConnectProperties(dbConnect.getProperties());

        return dataSource;
    }
}
