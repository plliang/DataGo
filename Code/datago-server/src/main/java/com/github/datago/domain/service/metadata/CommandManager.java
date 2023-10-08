package com.github.datago.domain.service.metadata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class CommandManager {

    private Map<String, Class<IDBQueryCommand>> commandMap = new HashMap<>();

    public Optional<IDBQueryCommand> get(String type) {
        Class<IDBQueryCommand> dbQueryCommandClass = commandMap.get(type);

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
