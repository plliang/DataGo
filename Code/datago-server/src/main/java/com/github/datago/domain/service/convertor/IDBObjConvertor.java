package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.DBObject;
import com.github.datago.domain.model.convertor.DataBaseMapping;

/**
 * 表转换接口
 */
public interface IDBObjConvertor<T extends DBObject> {

    /**
     * 对象转换方法
     * @param src 源对象
     * @return 转换后对象
     */
    public T convert(T src, DataBaseMapping mapping);
}
