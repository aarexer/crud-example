package com.github.aarexer.crud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Person {
    private final int id;
    private final String name;
    private final String phone;
}
