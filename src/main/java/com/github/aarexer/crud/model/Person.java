package com.github.aarexer.crud.model;

import lombok.Data;

@Data
public class Person {
    private final Long id;
    private final String name;
    private final String phone;
}
