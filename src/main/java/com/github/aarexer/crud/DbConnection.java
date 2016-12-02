package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Logger logger = LogManager.getLogger();

    private static Connection connection;
    private static String url = "jdbc:sqlite::resource:crud.db";

    private DbConnection() {
        createConnection();
    }

    public static synchronized Connection getConnection() {
        return connection != null ? connection : createConnection();
    }

    private static Connection createConnection() {
        logger.info("Create database connection");

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error("Can't create database connection, reason: {}", e);
            throw new RuntimeException(e);
        }

        logger.info("Connection created.");

        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection == null) {
            throw new IllegalStateException("Connection is null");
        }

        connection.close();
    }
}
