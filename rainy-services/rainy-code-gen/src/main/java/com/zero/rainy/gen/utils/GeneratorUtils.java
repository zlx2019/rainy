package com.zero.rainy.gen.utils;

import com.zero.rainy.core.utils.FormatterUtils;
import com.zero.rainy.db.constants.ColumnConstant;
import com.zero.rainy.gen.constant.ModelConstant;
import com.zero.rainy.gen.model.bo.ColumnBo;
import com.zero.rainy.gen.model.bo.TableBo;
import com.zero.rainy.gen.model.entity.Column;
import com.zero.rainy.gen.model.entity.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 代码生成工具
 *
 * @author Zero.
 * <p> Created on 2024/12/30 14:53 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneratorUtils implements ModelConstant {


    /**
     * 代码生成.
     *
     * @param table      数据表信息
     * @param moduleName 所属模块名称
     */
    public static void generate(Table table, String moduleName) {
        if (Objects.isNull(table) || table.getColumns().isEmpty()) {
            return;
        }
        // 读取配置文件
        PropertiesConfiguration config = getGenConfig();
        String tablePrefix = config.getString(TABLE_PREFIX);
        // 表名 -> 类名
        String className = tableNameToJavaName(table.getTableName(), tablePrefix);

        boolean hasBigDecimal = false;
        boolean hasLocalDateTime = false;
        boolean hasLocalDate = false;

        // 从表名得出相关类名信息
        TableBo tableBo = new TableBo(table);
        tableBo.setClassNamePascalCase(className);
        tableBo.setClassNameCamelCase(StringUtils.uncapitalize(className));
        List<ColumnBo> columns = new ArrayList<>(table.getColumns().size());

        // 处理表的字段
        for (Column column : table.getColumns()) {
            // 将列名转换为属性名
            ColumnBo bo = new ColumnBo(column);
            String attrName = columnNameToJavaName(column.getColumnName());
            bo.setAttrNamePascalCase(attrName);
            bo.setAttrNameCamelCase(StringUtils.uncapitalize(attrName));
            if (contains(bo.getAttrNameCamelCase())) {
                continue;
            }
            // 根据列类型设置属性类型
            String attrType = config.getString(bo.getColumnType(), UNKNOWN_TYPE);
            bo.setAttrType(attrType);
            // 是否为 特殊类型
            if (!hasBigDecimal && BigDecimal.class.getSimpleName().equals(attrType)) {
                hasBigDecimal = true;
            }
            if (!hasLocalDateTime && LocalDateTime.class.getSimpleName().equals(attrType)) {
                hasLocalDateTime = true;
            }
            if (!hasLocalDate && LocalDate.class.getSimpleName().equals(attrType)) {
                hasLocalDate = true;
            }
            // 是否为主键
            if (tableBo.getPrimaryKey() == null && PRIMARY_KEY.equalsIgnoreCase(bo.getPrimaryKey())) {
                tableBo.setPrimaryKey(bo);
            }
            columns.add(bo);
        }
        // 设置 资源加载器
        Properties props = new Properties();
        props.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(props);
        //封装模板数据
        Map<String, Object> data = new HashMap<>();
        data.put("tableName", tableBo.getTableName());
        data.put("comment", tableBo.getComment());
        data.put("primaryKey", tableBo.getPrimaryKey());
        data.put("className", tableBo.getClassNamePascalCase());
        data.put("classname", tableBo.getClassNameCamelCase());

        String requestPath = tableBo.getClassNameCamelCase();
        data.put("requestPath", requestPath);
        data.put("columns", columns);

        data.put("hasBigDecimal", hasBigDecimal);
        data.put("hasLocalDateTime", hasLocalDateTime);
        data.put("hashLocalDate", hasLocalDate);

        data.put(MODULE, moduleName);
        data.put(PACKAGE, config.getString(PACKAGE));
        data.put(AUTHOR, config.getString(AUTHOR));
        data.put(CREATED_AT, FormatterUtils.format(new Date()));
        VelocityContext context = new VelocityContext(data);
        // 渲染模板
        List<String> templates = getTemplates();
        for (String name : templates) {
            StringWriter writer = new StringWriter();
            Template template = Velocity.getTemplate(name, "UTF-8");
            template.merge(context, writer);
            System.out.println(writer.toString());
        }
    }

    /**
     * 表名 转 Java实体类名
     */
    public static String tableNameToJavaName(String tableName, String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            tableName = tableName.substring(prefix.length());
        }
        return columnNameToJavaName(tableName);
    }

    /**
     * 表字段名 转 Java属性名  sys_user -> sysUser
     */
    public static String columnNameToJavaName(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }


    /**
     * 读取 `generator.properties` 配置.
     */
    private static PropertiesConfiguration getGenConfig() {
        try {
            PropertiesConfiguration configuration = new PropertiesConfiguration("generator.properties");
            configuration.load();
            return configuration;
        } catch (ConfigurationException e) {
            throw new RuntimeException("read `generator.properties` config fail", e);
        }
    }

    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(TEMPLATE_PATH + FILE_NAME_MODEL);
//        templates.add(TEMPLATE_PATH+FILE_NAME_MAPPER);
//        templates.add(TEMPLATE_PATH+FILE_NAME_MAPPER_XML);
//        templates.add(TEMPLATE_PATH+FILE_NAME_SERVICE);
//        templates.add(TEMPLATE_PATH+FILE_NAME_SERVICE_IMPL);
        templates.add(TEMPLATE_PATH + FILE_NAME_CONTROLLER);
        return templates;
    }

    private static boolean contains(String columnName) {
        return Arrays.asList(ColumnConstant.ID,
                        ColumnConstant.CREATOR,
                        ColumnConstant.UPDATER,
                        ColumnConstant.CREATE_TIME,
                        ColumnConstant.UPDATE_TIME,
                        ColumnConstant.STATUS,
                        ColumnConstant.DELETED)
                .contains(columnName);
    }
}
