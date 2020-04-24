package aarexer.crud.dao;

import aarexer.crud.model.Person;

interface PersonDAO extends DAO<Person> {

    String TABLE_NAME = "person";

    String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY, NAME TEXT, PHONE TEXT)";

    String INSERT = "INSERT INTO " + TABLE_NAME + "(ID, NAME, PHONE) VALUES(?, ?, ?)";

    String REMOVE_BY_ID = "DELETE FROM " + TABLE_NAME + " WHERE id=?";

    String FIND_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

    String CLEAR_TABLE = "DELETE FROM " + TABLE_NAME;

    String FIND_ALL = "SELECT * FROM " + TABLE_NAME;
}
