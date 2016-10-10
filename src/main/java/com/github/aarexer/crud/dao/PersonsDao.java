package com.github.aarexer.crud.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonsDao {
    public void createTablePersons(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        // create a new table
        stmt.execute("CREATE TABLE IF NOT EXISTS PERSONS(ID INTEGER PRIMARY KEY, NAME TEXT, PHONE TEXT);");
    }
}
