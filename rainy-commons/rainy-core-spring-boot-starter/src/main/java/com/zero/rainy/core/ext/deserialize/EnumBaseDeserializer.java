package com.zero.rainy.core.ext.deserialize;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 枚举反序列化器
 *
 * @author Zero.
 * <p> Created on 2025/2/28 06:59 </p>
 */
@Slf4j
public class EnumBaseDeserializer extends JsonDeserializer<Enum<?>> {

    @Override
    public Enum<?> deserialize(JsonParser p, DeserializationContext ctx) throws IOException, JacksonException {
        Class<? extends Enum> enumType = (Class<? extends Enum>) ctx.getContextualType().getRawClass();
        String value = p.getValueAsString();
        try {
            // 尝试根据 Enum Name 转换
            return Enum.valueOf(enumType, value.trim());
        }catch (Exception e) {
            log.error("cannot by name deserialize enum {}, value is {}", enumType, value);
            try {
                // 尝试通过 Enum Ordinal 转换
                int ordinal = Integer.parseInt(value);
            }catch (NumberFormatException ex){
                log.error("cannot by ordinal deserialize enum {}, value is {}", enumType, value);
                // 尝试通过 @JsonValue 注解转换
                try {
                    for (Enum<?> enumConstant : enumType.getEnumConstants()) {
                        for (Field field : enumType.getDeclaredFields()) {
                            if (field.isAnnotationPresent(JsonValue.class)){
                                field.setAccessible(true);
                                Object fieldValue = field.get(enumConstant);
                                if (value.equals(String.valueOf(fieldValue))) {
                                    return enumConstant;
                                }
                            }
                        }
                    }
                }catch (Exception exc){
                    log.error("cannot by @JsonValue deserialize enum {}, value is {}", enumType, value);
                }
            }
        }
        return null;
    }
}
