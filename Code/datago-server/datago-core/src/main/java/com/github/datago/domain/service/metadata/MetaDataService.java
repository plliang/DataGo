package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.aggregate.DataBase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

@Component
public class MetaDataService {

    /**
     * 不同数据库的元数据查询逻辑实现
     */
    private List<IMetaDataCommand> metaDataCommandServiceList = new ArrayList<>();

    @PostConstruct
    public void init() {
        ServiceLoader<IMetaDataCommand> iMetaDataCommandServices = ServiceLoader.load(IMetaDataCommand.class);

        for (IMetaDataCommand metaDataCommandService : iMetaDataCommandServices) {
            metaDataCommandServiceList.add(metaDataCommandService);
        }
    }

    public void query(String commandKey, DataBase dataBase, DataSource dataSource) {
        for (IMetaDataCommand metaDataCommand : metaDataCommandServiceList) {
            if (metaDataCommand.support(commandKey, dataBase)) {
                metaDataCommand.doQuery(dataBase, dataSource);
            }
        }
    }
}
