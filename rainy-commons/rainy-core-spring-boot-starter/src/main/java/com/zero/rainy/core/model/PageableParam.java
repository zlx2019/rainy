package com.zero.rainy.core.model;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.enums.OrderBy;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 基础分页查询请求参数体
 *
 * @author Zero.
 * <p> Created on 2024/9/30 11:26 </p>
 */
@Getter
@Setter
public class PageableParam {

    /**
     * 页码
     */
    protected int page = Constant.PAGE;

    /**
     * 每页容量
     */
    protected int pageSize = Constant.PAGE_SIZE;

    /**
     * 时间范围查询，起始时间
     */
    protected LocalDateTime begin;

    /**
     * 时间范围查询，起始时间
     */
    protected LocalDateTime end;

    /**
     * 排序字段
     */
    protected String orderly;


    /**
     * 排序方式.
     */
    protected OrderBy orderBy = OrderBy.DESC;
}
