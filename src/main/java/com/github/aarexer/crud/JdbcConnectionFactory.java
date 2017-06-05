package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcConnectionFactory {
    private static Logger logger = LogManager.getLogger();
    private static final String URL = "jdbc:sqlite::resource:crud.db";

    private static Connection connection;

    private JdbcConnectionFactory() {
        throw new AssertionError("Fabric class.");
    }

    public static synchronized Connection getConnection() {
        return connection != null ? connection : createConnection();
    }

    private static Connection createConnection() {
        logger.info("Create database connection");

        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            logger.error("Can't create database connection, reason: {}", e);
            throw new RuntimeException(e);
        }

        logger.info("Connection created.");

        return connection;
    }
}
