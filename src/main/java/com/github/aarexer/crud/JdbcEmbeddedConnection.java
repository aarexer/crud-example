package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcEmbeddedConnection {
    private static Logger logger = LogManager.getLogger();
    private static final String URL = "jdbc:sqlite::resource:crud.db";

    private static Connection connection;

    private JdbcEmbeddedConnection() {
        throw new AssertionError("Fabric class.");
    }

    public static synchronized Connection getConnection() throws SQLException {
        return (connection != null && !connection.isClosed()) ? connection : createConnection();
    }

    private static Connection createConnection() {
        logger.info("Creating database connection");

        try (Connection tryConnected = DriverManager.getConnection(URL)) {
            // if it's all ok - assignment connection, otherwise - try to close it
            connection = tryConnected;
            logger.info("Connection created.");
        } catch (SQLException e) {
            logger.error("Can't create database connection, cause: {}", e);
            throw new RuntimeException("Can't create database connection", e);
        }

        return connection;
    }
}
