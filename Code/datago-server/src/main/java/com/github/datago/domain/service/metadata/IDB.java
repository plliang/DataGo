package com.github.datago.domain.service.metadata;

import com.github.datago.domain.model.entity.Schema;
import com.github.datago.domain.model.entity.Sequence;
import com.github.datago.domain.model.entity.Table;
import com.github.datago.infrastructure.constant.metadata.CaseSensitiveMode;

import javax.sql.DataSource;
import java.util.Map;

public interface IDB {

    /**
     * 查询数据库大小写敏感模式
     */
    public CaseSensitiveMode caseSensitiveMode();

    /**
     * 查询数据库下有哪些模式
     * @param dataSource 数据库连接池
     * @return schema的Map对象，此方法不包含schema下具体对象的查询
     */
    public Map<String, Schema> findSchemas(DataSource dataSource);

    /**
     * 查询指定schema下的所有sequence
     * @param dataSource 数据库连接池
     * @param schemaMap 待查询的schema
     * @return sequence集合
     */
    public Map<String, Sequence> findSequences(DataSource dataSource, Map<String, Schema> schemaMap);

    /**
     * 查询指定schema下的sequences
     * @param dataSource 数据库连接池
     * @param schema 指定查询的schema
     * @return 指定schema下的sequence集合
     */
    public Map<String, Sequence> findSequences(DataSource dataSource, Schema schema);

    /**
     * 查询指定schema下的tables
     * @param dataSource 数据库连接池
     * @param schemaMap 指定的schema集合
     */
    public void findTables(DataSource dataSource, Map<String, Schema> schemaMap);

    /**
     * 查询指定schema下的表对象
     * @param dataSource 数据库连接池
     * @param schema 指定的schema
     * @return table集合
     */
    public Map<String, Table> findTables(DataSource dataSource, Schema schema);

    public void findColumns(Map<String, Table> tableMap);
}
