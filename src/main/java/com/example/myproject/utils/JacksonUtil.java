package com.example.myproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.util.List;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeFactory typeFactory = objectMapper.getTypeFactory();

    /**
     * 将对象转换为Json字符串
     * @param object 要转换的对象
     * @return 转换后的Json字符串
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json字符串转换为对象
     * @param jsonString Json字符串
     * @param clazz 要转换成的对象类型
     * @param <T> 对象类型
     * @return 转换后的对象
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转换为Map
     * @param object 要转换的对象
     * @return 转换后的Map
     */
    public static Map<String, Object> toMap(Object object) {
        try {
            return objectMapper.convertValue(object, Map.class);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Map转换为对象
     * @param map 要转换的Map
     * @param clazz 要转换成的对象类型
     * @param <T> 对象类型
     * @return 转换后的对象
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    /**
     * 将Json字符串转换为List对象
     * @param json Json字符串
     * @param elementType 要转换成的List对象类型
     * @param <T> List对象类型
     * @return 转换后的List对象
     */

    public static <T> List<T> jsonToList(String json, Class<T> elementType) throws Exception {
        JavaType collectionType = typeFactory.constructCollectionType(List.class, elementType);
        return objectMapper.readValue(json, collectionType);
    }


    public static String listToJson(List<?> list) throws JsonProcessingException {
        return objectMapper.writeValueAsString(list);
    }
}
