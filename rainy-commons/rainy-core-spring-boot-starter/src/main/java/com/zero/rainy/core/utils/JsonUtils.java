package com.zero.rainy.core.utils;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
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

/**
 * JSON 工具类
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:32 </p>
 */
@Slf4j
public class JsonUtils {

    /**
     * Jackson 实例
     */
    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
        // 序列化时,对象属性为NULL的不序列化
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 反序列化时 忽略json中存在 但对象中不存在的字段
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 命名规则为驼峰命名
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        timeModule.addSerializer(new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        timeModule.addSerializer(new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
        MAPPER.registerModule(timeModule);
    }


    /**
     * Object To Json
     *
     * @param bean  Object
     * @param <T>   泛型
     * @return      Json
     */
    @SneakyThrows
    public static <T> String toJson(T bean) {
        return MAPPER.writeValueAsString(bean);
    }


    /**
     * Json To Object
     * @param value Json
     * @param clazz 泛型
     * @return      Object
     */
    @SneakyThrows
    public static <T> T toObj(String value, Class<T> clazz) {
        return MAPPER.readValue(value, clazz);
    }

    @SneakyThrows
    public static <T> T toObj(byte[] value, Class<T> clazz){
        return MAPPER.readValue(value, clazz);
    }

    @SneakyThrows
    public static <T> T toObj(byte[] value, TypeReference<T> typeReference){
        return MAPPER.readValue(value, typeReference);
    }

    /**
     * Json输入流，序列化为实体
     *
     * @param src   输入流
     * @param clazz 实体原型
     */
    @SneakyThrows
    public static <T> T toObj(InputStream src, Class<T> clazz)  {
        return MAPPER.readValue(src, clazz);
    }

    /**
     * Json 通过泛型动态反序列化为指定的实体
     *
     * @param value Json
     * @param type  泛型推到
     * @param <T>   泛型
     */
    @SneakyThrows
    public static <T> T toObj(String value, TypeReference<T> type)  {
        return MAPPER.readValue(value, type);
    }

    @SneakyThrows
    public static <T> T toObj(InputStream src, TypeReference<T> type) {
        return MAPPER.readValue(src, type);
    }

    /**
     * Json To Collection
     *
     * @param value    Json
     * @param listType 集合类型
     * @param clazz    集合泛型
     * @return
     */
    @SneakyThrows
    public static <T, L extends Collection> L readValueToList(String value, Class<L> listType, Class<T> clazz) {
        CollectionType collectionType = MAPPER.getTypeFactory().constructCollectionType(listType, clazz);
        return MAPPER.readValue(value, collectionType);
    }

    /**
     * Json To List
     * @param value Json
     * @param clazz 集合泛型
     * @return      List
     */
    @SneakyThrows
    public static <T> List<T> readValueToList(String value, Class<T> clazz){
        CollectionType listType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
        return MAPPER.readValue(value,listType);
    }

    /**
     * Json To Map
     *
     * @param value     Json
     * @param mapType   Map 类型
     * @param keyType   Key 类型
     * @param valueType Value 类型
     * @return Map容器
     */
    @SneakyThrows
    public static  <M extends Map, K, V> M readValueToMap(String value, Class<M> mapType, Class<K> keyType, Class<V> valueType) {
        MapType mapKind = MAPPER.getTypeFactory().constructMapType(mapType, keyType, valueType);
        return MAPPER.readValue(value, mapKind);
    }

    /**
     * 将 Json 字符串解析为 Json节点树
     * @param json  json字符串
     */
    @SneakyThrows
    public static JsonNode parseJsonNode(String json) {
        return MAPPER.readTree(json);
    }

    public static ObjectNode parseObjectNode(String json) {
        return (ObjectNode) parseJsonNode(json);
    }
}
