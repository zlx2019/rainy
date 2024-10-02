package com.zero.rainy.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zero.rainy.db.hooks.SupplementEntityHook;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * MybatisPlus 默认配置
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:39 </p>
 */
@Import(SupplementEntityHook.class)
@MapperScan("com.zero.rainy.*.mapper")
public class DefaultMybatisPlusConfig {

    /**
     * 注册 Mybatis Plus 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加非法SQL拦截器
//        interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        // 防止全表更新或删除插件.
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页插件，如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
