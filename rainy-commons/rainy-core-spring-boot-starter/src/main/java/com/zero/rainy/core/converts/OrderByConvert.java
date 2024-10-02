package com.zero.rainy.core.converts;

import com.zero.rainy.core.enums.OrderBy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 排序方式枚举 {@link OrderBy} 转换器
 *
 * @author Zero.
 * <p> Created on 2024/9/30 13:01 </p>
 */
public class OrderByConvert implements Converter<String, OrderBy> {

    @Override
    public OrderBy convert(String source) {
        return OrderBy.getByName(source);
    }
}
