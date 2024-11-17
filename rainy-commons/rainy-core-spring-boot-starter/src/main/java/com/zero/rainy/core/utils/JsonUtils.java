package com.zero.rainy.core.utils;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Json tools
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:32 </p>
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unused"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtils {
    private static final ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        // 序列化时,对象属性为NULL的不序列化
        // MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化时 忽略json中存在 但对象中不存在的字段
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 禁止将时间类型序列化为时间戳
        MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 全局序列化命名策略
        // MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE); // 小写 + _ 命名规则
        // MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE); // 驼峰, 首字母小写

        // 时间类型序列化器
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        timeModule.addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        timeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        MAPPER.registerModule(timeModule);

        // 类型序列化模块
        SimpleModule typeModule = new SimpleModule();
        typeModule.addSerializer(Long.class, ToStringSerializer.instance);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }


    /**
     * Convert any object to json
     *
     * @param value Any object
     * @return Json
     */
    @SneakyThrows
    public static <T> String marshal(T value) {
        return MAPPER.writeValueAsString(value);
    }
    @SneakyThrows
    public static <T> String marshal(T bean, ObjectMapper mapper) {
        if (Objects.nonNull(mapper)) {
            return mapper.writeValueAsString(bean);
        }
        return MAPPER.writeValueAsString(bean);
    }

    /**
     * Json to object by type
     *
     * @param value Json
     * @param clazz Object type
     * @return Object
     */
    @SneakyThrows
    public static <T> T unmarshal(String value, Class<T> clazz) {
        return MAPPER.readValue(value, clazz);
    }
    @SneakyThrows
    public static <T> T unmarshal(String value, Class<T> clazz, ObjectMapper mapper) {
        if (Objects.nonNull(mapper)) {
            return mapper.readValue(value, clazz);
        }
        return MAPPER.readValue(value, clazz);
    }

    /**
     * Json bytes to object by type
     */
    @SneakyThrows
    public static <T> T unmarshal(byte[] value, Class<T> clazz) {
        return MAPPER.readValue(value, clazz);
    }

    /**
     * Json bytes to object by typeReference
     */
    @SneakyThrows
    public static <T> T unmarshal(byte[] value, TypeReference<T> typeReference) {
        return MAPPER.readValue(value, typeReference);
    }

    @SneakyThrows
    public static <T> T unmarshal(String value, TypeReference<T> type) {
        return MAPPER.readValue(value, type);
    }

    @SneakyThrows
    public static <T> T unmarshal(InputStream src, TypeReference<T> type) {
        return MAPPER.readValue(src, type);
    }

    /**
     * Json input stream is converted to object according to type
     *
     * @param input Json input
     */
    @SneakyThrows
    public static <T> T unmarshal(InputStream input, Class<T> clazz) {
        return MAPPER.readValue(input, clazz);
    }


    /**
     * Json to sequence type
     *
     * @param value    Json value
     * @param listType Sequence type
     * @param clazz    Elem type
     */
    @SneakyThrows
    public static <T, L extends Collection<?>> L readValueToList(String value, Class<L> listType, Class<T> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(listType, clazz);
        return MAPPER.readValue(value, collectionType);
    }

    /**
     * Json To List
     *
     * @param value Json value
     * @param clazz Elem type
     */
    @SneakyThrows
    public static <T> List<T> readValueToList(String value, Class<T> clazz) {
        CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return MAPPER.readValue(value, listType);
    }

    /**
     * Json To Map
     *
     * @param value     Json
     * @param mapType   Map type
     * @param keyType   Key type
     * @param valueType Value type
     * @return Map容器
     */
    @SneakyThrows
    public static <M extends Map, K, V> M readValueToMap(String value, Class<M> mapType, Class<K> keyType, Class<V> valueType) {
        MapType mapKind = MAPPER.getTypeFactory().constructMapType(mapType, keyType, valueType);
        return MAPPER.readValue(value, mapKind);
    }

    /**
     * 将 Json 字符串解析为 Json节点树
     *
     * @param json json字符串
     */
    @SneakyThrows
    public static JsonNode parseJsonNode(String json) {
        return MAPPER.readTree(json);
    }

    public static ObjectNode parseObjectNode(String json) {
        return (ObjectNode) parseJsonNode(json);
    }
}
