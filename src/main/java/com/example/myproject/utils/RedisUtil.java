package com.example.myproject.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private ObjectMapper objectMapper;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = new ObjectMapper();
    }
    /**
     * 获取字符串类型的值
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    /**
     * 存储字符串类型的值
     * @param key 键
     * @param value 值
     * @param expire 过期时间（单位：秒）
     */
    public void setString(String key, String value, long expire) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(expire));
    }

    /**
     * 获取哈希类型的值
     * @param key 键
     * @param hashKey 哈希键
     * @return 哈希值
     */
    public Object getHash(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 存储哈希类型的值
     * @param key 键
     * @param hashKey 哈希键
     * @param value 哈希值
     */
    public void setHash(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

//    /**
//     * 获取列表类型的值
//     * @param key 键
//     * @return 列表值
//     */
//    public List<Object> getList(String key) {
//        return redisTemplate.opsForList().range(key, 0, -1);
//    }

//    /**
//     * 存储列表类型的值
//     * @param key 键
//     * @param  列表值
//     */
//    public void setList(String key, Object value) {
//        redisTemplate.opsForList().rightPush(key, value);
//    }
    public <T> void setList(String key, List<T> list) throws Exception {
        String json = objectMapper.writeValueAsString(list);
        redisTemplate.opsForValue().set(key, json);
    }

    public <T> List<T> getList(String key, TypeReference<List<T>> type) throws Exception {
        String json = (String) redisTemplate.opsForValue().get(key);
        if (json == null || json.isEmpty()) {
            return null;
        }
        List<T> list = objectMapper.readValue(json, type);
        return list;
    }
    /**
     * 获取集合类型的值
     * @param key 键
     * @return 集合值
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 存储集合类型的值
     * @param key 键
     * @param value 集合值
     */
    public void setSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取有序集合类型的值
     * @param key 键
     * @return 有序集合值
     */
    public Set<Object> getSortedSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * 存储有序集合类型的值
     * @param key 键
     * @param value 有序集合值
     * @param score 分值
     */
    public void setSortedSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }
    public void putObject(String key, Object object) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String, Object> map = JacksonUtil.toMap(object);
        hashOps.putAll(key, map);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Map<String, Object> map = hashOps.entries(key);
        if (map == null || map.isEmpty()) {
            return null;
        }
        return JacksonUtil.fromMap(map, clazz);
    }
}
