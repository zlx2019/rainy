package com.zero.rainy.gen.mapper;

import com.zero.rainy.db.ext.mapper.SuperMapper;
import com.zero.rainy.gen.model.entity.Column;
import com.zero.rainy.gen.model.entity.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 15:09 </p>
 */
@Mapper
public interface CodeGeneratorMapper extends SuperMapper {

    /**
     * 查询所有表信息
     */
    List<Table> queryAllTables(@Param("name") String tableName);

    /**
     * 查询数据库的总表数
     */
    int tablesTotal(@Param("name") String tableName);

    /**
     * 根据表查询所有的列信息
     * @param tableName 表名
     */
    List<Column> queryColumnsByTable(@Param("name") String tableName);
}
