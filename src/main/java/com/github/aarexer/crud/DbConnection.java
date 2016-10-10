package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Logger logger = LogManager.getLogger();

    private static DbConnection dbConnection;
    private Connection connection;
    private static String url = "jdbc:sqlite:crud.db";

    public DbConnection() {
        dbConnection = this;
        createConnection();
    }

    public static synchronized DbConnection getInstance() {
        return dbConnection != null ? dbConnection : new DbConnection();
    }

    private void createConnection() {
        logger.info("Create database connection");
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Can't create database connection, reason: {}", e);
            throw new RuntimeException(e);
        }
        logger.info("Create connection.");
    }

    public Connection getConnection() {
        return connection;
    }
}
