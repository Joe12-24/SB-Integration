package com.example.myproject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private String address;
    private Integer age;

    public User() {}

    public User(Long id, String name, Integer age,String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    // getter and setter methods
}
