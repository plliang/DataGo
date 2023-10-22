package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;

import javax.sql.DataSource;

public interface IMetaDataCommand {

    public boolean support(String commandKey, DataBase dataBase);

    public void doQuery(DataBase dataBase, DataSource dataSource);
}
