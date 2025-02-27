package com.zero.rainy.web.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


/**
 * 通用视图对象
 *
 * @author Zero.
 * <p> Created on 2024/12/31 12:43 </p>
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseVo {
    /**
     * 业务数据ID
     */
    private Long id;
}
