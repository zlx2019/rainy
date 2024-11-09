package com.zero.rainy.core.entity;

import com.zero.rainy.core.entity.supers.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 动态配置表实体
 *
 * @author Zero.
 * <p> Created on 2024/11/6 22:06 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Config extends SuperEntity<Config> {

    /**
     * 配置Key
     */
    private String key;

    /**
     * 配置Value (Json格式)
     */
    private String value;

    /**
     * 配置备注
     */
    private String remark;
}
