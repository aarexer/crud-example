package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MainApp {
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws SQLException {
        DbConnection dbConnection = DbConnection.getInstance();

    }
}
