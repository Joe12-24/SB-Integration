package com.example.myproject;

import com.example.myproject.dto.User;
import com.example.myproject.utils.RedisUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void testString() {
        String key = "testString";
        String value = "Hello, Redis!";
        // set string
        redisUtil.setString(key, value, 600);
        // get string
        String result = redisUtil.getString(key);
        System.out.println(result);
    }

    @Test
    public void testList() throws Exception {
        String key = "testList";
        // clear list
        // push elements to list
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        redisUtil.setList(key, list);
        // get list size
        List<Object> objList = redisUtil.getList("testList",new TypeReference<List<Object>>() {});
        List<String> strList = new ArrayList<>();
        for (Object obj : objList) {
            strList.add(obj.toString());
        }
        strList.forEach(System.out::println);
        System.out.println(redisUtil.getList("testList",new TypeReference<List<String>>() {}));
    }

    @Test
    void testPutAndGet() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setAge(20);
        user.setAddress("Shanghai");
        User user2 = new User();
        user2.setId(1L);
        user2.setName("John");
        user2.setAge(20);
        user2.setAddress("Shanghai");

        redisUtil.putObject("user:1", user);

        User result = redisUtil.getObject("user:1", User.class);
        System.out.println(result);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        redisUtil.setList("username", userList);
        List<User> objList = redisUtil.getList("username",new TypeReference<List<User>>() {});


        objList.forEach(System.out::println);
    }

}