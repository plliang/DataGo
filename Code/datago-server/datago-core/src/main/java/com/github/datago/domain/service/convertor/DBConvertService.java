package com.github.datago.domain.service.convertor;

import com.github.datago.domain.model.aggregate.DataMapping;
import com.github.datago.domain.model.entity.DBObject;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.ServiceLoader;

public class DBConvertService {

    private List<IConvertor<? extends DBObject>> convertorList;

    @PostConstruct
    public void init() {
        ServiceLoader<IConvertor> convertors = ServiceLoader.load(IConvertor.class);
        for (IConvertor convertor : convertors) {
            convertorList.add(convertor);
        }
    }

    public void convert(DataMapping mapping, List<? extends DBObject> objList) {
        if (!CollectionUtils.isEmpty(objList)) {
            for (IConvertor<? extends DBObject> convertor : convertorList) {
                if (convertor.support(mapping, objList.get(0).getClass())) {
                    convertor.convert(mapping, objList);
                }
            }
        }
    }
}
