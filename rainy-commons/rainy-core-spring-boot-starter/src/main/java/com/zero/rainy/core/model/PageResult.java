package com.zero.rainy.core.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zero.rainy.core.utils.CloneUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 分页响应实体
 *
 * @author Zero.
 * <p> Created on 2024/10/1 08:48 </p>
 */
@Getter
@Setter
@Accessors(chain = true)
public class PageResult<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总数据量
     */
    private long total;
    /**
     * 页码
     */
    private long page;
    /**
     * 页容量
     */
    private long pageSize;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     * 页数据
     */
    private Collection<T> list;

    /**
     * 将 {@link IPage} 分页结果集，转换为 {@link PageResult} 响应体
     * @param page  分页数据
     */
    public static <T> PageResult<T> of(IPage<T> page) {
        return new PageResult<T>()
                .setTotal(page.getTotal())
                .setPage(page.getCurrent())
                .setPageSize(page.getSize())
                .setTotalPage(page.getPages())
                .setList(page.getRecords());
    }

    public static <V> PageResult<V> of(IPage<?> page, Collection<V> list) {
        return new PageResult<V>()
                .setTotal(page.getTotal())
                .setPage(page.getCurrent())
                .setPageSize(page.getSize())
                .setTotalPage(page.getPages())
                .setList(list);
    }

    public static <T,V> PageResult<V> of(IPage<T> page, Class<V> voClass) {
        List<V> list = CloneUtils.copyProperties(page.getRecords(), voClass);
        return of(page, list);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<T>();
    }
}
