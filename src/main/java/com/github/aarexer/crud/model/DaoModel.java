package com.github.aarexer.crud.model;

import lombok.Data;

@Data
public abstract class DaoModel {
    private int id;

    public DaoModel(int id) {
        this.id = id;
    }
}
