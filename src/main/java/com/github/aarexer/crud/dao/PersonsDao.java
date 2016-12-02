package com.github.aarexer.crud.dao;

import com.github.aarexer.crud.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.aarexer.crud.dao.Queries.StudentTable.TABLE_NAME;

public class PersonsDao implements DAO<Person> {
    private static final Logger logger = LogManager.getLogger();

    private final Connection conn;
    private final PreparedStatement removePrepStatement;
    private final PreparedStatement addPrepStatement;
    private final PreparedStatement getByIdPrepStatement;

    public PersonsDao(Connection conn) {
        this.conn = conn;

        try {
            this.removePrepStatement = conn.prepareStatement(Queries.StudentTable.REMOVE_BY_ID);
            this.addPrepStatement = conn.prepareStatement(Queries.StudentTable.ADD);
            this.getByIdPrepStatement = conn.prepareStatement(Queries.StudentTable.GET_BY_ID);
        } catch (SQLException ex) {
            logger.error("Can't create prepared statement");
            throw new IllegalStateException(ex);
        }
    }

    public void createTable() throws SQLException {
        logger.info("Trying to create table {}", TABLE_NAME);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(Queries.StudentTable.CREATE);

            logger.info("Table {} created.", TABLE_NAME);
        }
    }

    @Override
    public synchronized void add(Person person) throws SQLException {
        logger.info("Adding {}", person);

        addPrepStatement.setLong(1, person.getId());
        addPrepStatement.setString(2, person.getName());
        addPrepStatement.setString(3, person.getPhone());

        logger.info("Element {} added to {} table", person, TABLE_NAME);
    }

    @Override
    public synchronized void remove(int id) throws SQLException {
        logger.info("Remove person by id: {}", id);

        removePrepStatement.setLong(1, id);

        if (removePrepStatement.executeUpdate() > 0) {
            logger.info("Element with id {} removed from {} table", id, TABLE_NAME);
        } else {
            logger.error("Can't remove element with id {} from {} table", id, TABLE_NAME);
        }

        logger.info("Person with id: {} removed", id);
    }

    @Override
    public synchronized Optional<Person> get(int id) throws SQLException {
        logger.info("Get person by id: {}", id);

        getByIdPrepStatement.setLong(1, id);

        ResultSet res = getByIdPrepStatement.executeQuery();
        if (res.next()) {
            final String name = res.getString(2);
            final String phone = res.getString(3);

            logger.info("Person with id: {} returned", id);

            return Optional.of(new Person(id, name, phone));
        } else {
            logger.info("Person with id: {} doesn't exist", id);
            return Optional.empty();
        }
    }

    @Override
    public synchronized List<Person> getAll() throws SQLException {
        logger.info("Trying to get all elements from table: {}", TABLE_NAME);

        try (Statement stmt = conn.createStatement()) {
            final ResultSet rs = stmt.executeQuery(Queries.StudentTable.GET_ALL);
            final List<Person> persons = new ArrayList<>();

            while (rs.next()) {
                final int id = rs.getInt(0);
                final String name = rs.getString(1);
                final String phone = rs.getString(2);
                persons.add(new Person(id, name, phone));
            }

            if (persons.size() > 0) {
                logger.info("Return {} rows after get all query for table {}", persons.size(), TABLE_NAME);
            } else {
                logger.info("Empty list after get all query for table {}", TABLE_NAME);
            }
            return persons;
        }
    }

    @Override
    public synchronized void clear() throws SQLException {
        logger.info("Trying to clear table {}", TABLE_NAME);

        try (Statement stmt = conn.createStatement()) {
            final int numOfRows = stmt.executeUpdate(Queries.StudentTable.CLEAR_TABLE);

            logger.info("Table {} cleared, removed {} rows.", TABLE_NAME, numOfRows);
        }
    }

    @Override
    public synchronized void addAll(List<Person> elements) throws SQLException {
        logger.info("Trying to add elements {} to the table {}", elements, TABLE_NAME);
        for (Person person : elements) {
            add(person);
        }
        logger.info("Added all elements to the table {}", TABLE_NAME);
    }
}
