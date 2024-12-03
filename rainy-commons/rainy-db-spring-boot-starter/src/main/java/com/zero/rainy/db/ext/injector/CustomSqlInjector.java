package com.zero.rainy.db.ext.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.zero.rainy.db.ext.methods.BatchInsert;

import java.util.List;
import java.util.stream.Stream;

/**
 * 扩展 MybatisPlus 注入器
 *
 * @author Zero.
 * <p> Created on 2024/12/1 12:06 </p>
 */
public class CustomSqlInjector extends DefaultSqlInjector {


    /**
     * 注册 Mapper 通用方法
     *
     * @param mapperClass 当前mapper
     * @param tableInfo   表信息
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodAll = super.getMethodList(mapperClass, tableInfo);
        List<AbstractMethod> methods = Stream.<AbstractMethod>builder()
                .add(new BatchInsert()) // 添加批量插入注入器
                .build().toList();
        methodAll.addAll(methods);
        return methodAll;
    }
}
