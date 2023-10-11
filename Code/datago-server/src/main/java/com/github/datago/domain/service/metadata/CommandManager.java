package com.github.datago.domain.service.metadata;

import com.github.datago.domain.service.metadata.mysql.ColumnsQueryCommand;
import com.github.datago.domain.service.metadata.mysql.SchemaQueryCommand;
import com.github.datago.domain.service.metadata.mysql.TableQueryCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CommandManager {

    private final Map<String, Class<? extends IDBQueryCommand>> commandMap = new HashMap<>();

    @PostConstruct
    void init() {
        commandMap.put("schemas", SchemaQueryCommand.class);
        commandMap.put("tables", TableQueryCommand.class);
        commandMap.put("columns", ColumnsQueryCommand.class);
    }

    public Optional<IDBQueryCommand> get(String type) {
        Class<? extends IDBQueryCommand> dbQueryCommandClass = commandMap.get(type);

        if (dbQueryCommandClass != null) {
            try {
                IDBQueryCommand queryCommand = dbQueryCommandClass.getDeclaredConstructor().newInstance();

                return Optional.of(queryCommand);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                log.error("查询指令初始化失败", e);
            }
        }
        return Optional.empty();
    }
}
