package com.zero.rainy.sample;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.Method;

/**
 * @author Zero.
 * <p> Created on 2025/2/28 00:00 </p>
 */
public class String2EnumConvertFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new String2EnumConverter<>(targetType);
    }

    private static class String2EnumConverter<T extends Enum> implements Converter<String, T> {
        private final Class<T> type;
        public String2EnumConverter(Class<T> type) {
            this.type = type;
        }
        @Override
        public T convert(String source) {
            if (source.isEmpty()){
                return null;
            }
            try {
                // 通过 Enum Name 转换
                return (T)Enum.valueOf(this.type, source.trim());
            }catch (Exception e){
                try {
                    // 通过 Ordinal 转换
                    int ordinal = Integer.parseInt(source.trim());
                    return this.type.getEnumConstants()[ordinal];
                }catch (Exception ex){
                    try {
                        Method from = this.type.getDeclaredMethod("from", String.class);
                        return (T) from.invoke(null, source);
                    }catch (Exception exc){
                        return null;
                    }
                }
            }
        }
    }
}
