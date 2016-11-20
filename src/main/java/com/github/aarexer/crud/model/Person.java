package com.github.aarexer.crud.model;

import lombok.Data;

@Data
public class Person {
    private int id;
    private String name;
    private String phone;

    public Person(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}
