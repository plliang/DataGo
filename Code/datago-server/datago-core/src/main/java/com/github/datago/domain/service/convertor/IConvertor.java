package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.aggregate.DataMapping;
import com.github.datago.domain.model.entity.DBObject;

import java.util.List;

public interface IConvertor<T extends DBObject> {

    public boolean support(DataMapping dataMapping, Class type);

    public T convert(DataMapping dataMapping, List<? extends DBObject> obj);

}
