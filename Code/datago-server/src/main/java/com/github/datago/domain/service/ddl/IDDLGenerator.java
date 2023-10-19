package com.github.datago.domain.service.ddl;

import com.github.datago.domain.model.DBObject;

/**
 * DDL生成器
 */
public interface IDDLGenerator<T extends DBObject> {

    public String generate(T t);
}