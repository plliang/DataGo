package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.DBObject;

/**
 * 表转换接口
 */
public interface IDBObjConvertor<T extends DBObject, P extends DBObject> {

    /**
     * 对象转换方法
     * @param src 源对象
     * @return 转换后对象
     */
    public T convert(T src, P parent);
}
