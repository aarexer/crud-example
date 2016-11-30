package com.github.aarexer.crud.model;

import lombok.Data;

@Data
public class Person extends DaoModel {
    private String name;
    private String phone;

    public Person(int id, String name, String phone) {
        super(id);
        this.name = name;
        this.phone = phone;
    }
}
