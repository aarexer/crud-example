package com.github.aarexer.crud.dao;

final class Queries {
    private Queries() {
        throw new AssertionError("Class for static can't have instances!");
    }

    static final class Persons {
        private Persons() {
            throw new AssertionError("Class for static can't have instances!");
        }

        static final String TABLE_NAME = "PERSONS";

        static final String CREATE = String.format(
                "CREATE TABLE IF NOT EXISTS %s(ID INTEGER PRIMARY KEY, NAME TEXT, PHONE TEXT);", TABLE_NAME
        );

        static final String ADD = String.format("INSERT INTO %s(ID, NAME, PHONE) VALUES(?, ?, ?);", TABLE_NAME);

        static final String REMOVE_BY_ID = String.format("DELETE FROM %s WHERE ID=?;", TABLE_NAME);

        static final String GET_BY_ID = String.format("SELECT * FROM %s WHERE ID=?;", TABLE_NAME);

        static final String CLEAR_TABLE = String.format("DELETE FROM %s;", TABLE_NAME);

        static final String GET_ALL = String.format("SELECT * FROM %s;", TABLE_NAME);
    }
}
