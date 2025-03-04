package com.zero.rainy.core.model.entity;

import com.zero.rainy.core.enums.ConfigType;
import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.model.entity.supers.SuperEntityExt;
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
public class Config extends SuperEntityExt<Config> {

    /**
     * 配置唯一标识
     */
    private DynamicPropertiesKey configKey;

    /**
     * 配置内容
     */
    private String configValue;

    /**
     * 配置类型
     * @see ConfigType
     */
//    @TableField(typeHandler = EnumOrdinalTypeHandler.class)
    private ConfigType configType;

    /**
     * 配置备注
     */
    private String remark;
}
