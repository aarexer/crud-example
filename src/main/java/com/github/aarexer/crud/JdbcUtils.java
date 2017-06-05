package com.github.aarexer.crud;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public final class JdbcUtils {
    private static Logger logger = LogManager.getLogger();

    private JdbcUtils() {
        throw new AssertionError("Fabric class.");
    }

    public static void close(Connection connection) throws SQLException {
        logger.info("Closing connection.");

        if (Objects.isNull(connection)) {
            throw new IllegalArgumentException("Connection is null.");
        }

        connection.close();

        logger.info("Connection closed.");
    }
}
