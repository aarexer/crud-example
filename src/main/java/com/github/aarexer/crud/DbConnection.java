package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DbConnection {
    //todo rewrite all
    private static Logger logger = LogManager.getLogger();
    private static final String URL = "jdbc:sqlite::resource:crud.db";

    private static Connection connection;

    private DbConnection() {
        createConnection();
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

    public static void closeConnection() throws SQLException {
        logger.info("Closing connection.");

        if (Objects.isNull(connection)) {
            throw new IllegalArgumentException("Connection is null.");
        }

        connection.close();

        logger.info("Connection closed.");
    }
}
