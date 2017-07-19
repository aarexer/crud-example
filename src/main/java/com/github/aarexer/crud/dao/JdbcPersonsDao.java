package com.github.aarexer.crud.dao;

import com.github.aarexer.crud.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.aarexer.crud.dao.Queries.Persons.TABLE_NAME;

public class JdbcPersonsDao implements PersonDao {
    private static final Logger logger = LogManager.getLogger();

    private final Connection connection;
    private final PreparedStatement removePrepStatement;
    private final PreparedStatement addPrepStatement;
    private final PreparedStatement getByIdPrepStatement;

    public JdbcPersonsDao(Connection connection) {
        this.connection = connection;

        try {
            this.removePrepStatement = connection.prepareStatement(Queries.Persons.REMOVE_BY_ID);
            this.addPrepStatement = connection.prepareStatement(Queries.Persons.ADD);
            this.getByIdPrepStatement = connection.prepareStatement(Queries.Persons.GET_BY_ID);
        } catch (SQLException ex) {
            logger.error("Can't create prepared statement");
            throw new DaoInitializationException("Can't create prepared statement", ex);
        }
    }

    public void createTable() throws SQLException {
        logger.info("Trying to create table {}", TABLE_NAME);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(Queries.Persons.CREATE);

            logger.info("Table {} created.", TABLE_NAME);
        }
    }

    @Override
    public synchronized void add(Person person) throws SQLException {
        logger.debug("Adding {} to database", person);

        addPrepStatement.setLong(1, person.getId());
        addPrepStatement.setString(2, person.getName());
        addPrepStatement.setString(3, person.getPhone());

        addPrepStatement.executeUpdate();

        logger.debug("Person {} added to {} table", person, TABLE_NAME);
    }

    @Override
    public synchronized void remove(Long id) throws SQLException {
        logger.debug("Remove person by id: {}", id);

        removePrepStatement.setLong(1, id);

        if (removePrepStatement.executeUpdate() > 0) {
            logger.debug("Element with id {} removed from {} table", id, TABLE_NAME);
        } else {
            logger.error("Can't remove element with id {} from {} table", id, TABLE_NAME);
        }
    }

    @Override
    public synchronized Optional<Person> get(Long id) throws SQLException {
        logger.debug("Get person by id: {}", id);

        getByIdPrepStatement.setLong(1, id);

        ResultSet res = getByIdPrepStatement.executeQuery();
        if (res.next()) {
            String name = res.getString("name");
            String phone = res.getString("phone");

            logger.debug("Person with id: {} returned", id);

            return Optional.of(new Person(id, name, phone));
        } else {
            logger.warn("Person with id: {} doesn't exist", id);
            return Optional.empty();
        }
    }

    @Override
    public synchronized List<Person> getAll() throws SQLException {
        logger.debug("Trying to get all elements from table: {}", TABLE_NAME);

        try (Statement stmt = connection.createStatement()) {
            final ResultSet rs = stmt.executeQuery(Queries.Persons.GET_ALL);
            final List<Person> persons = new ArrayList<>();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                persons.add(new Person(id, name, phone));
            }

            if (persons.size() > 0) {
                logger.debug("Return {} rows after get all query for table {}", persons.size(), TABLE_NAME);
            } else {
                logger.warn("Empty list after get all query for table {}", TABLE_NAME);
            }

            return persons;
        }
    }

    @Override
    public synchronized void clear() throws SQLException {
        logger.debug("Trying to clear table {}", TABLE_NAME);

        try (Statement stmt = connection.createStatement()) {
            final int numOfRows = stmt.executeUpdate(Queries.Persons.CLEAR_TABLE);

            logger.debug("Table {} cleared, removed {} rows.", TABLE_NAME, numOfRows);
        }
    }

    @Override
    public synchronized void addAll(List<Person> elements) throws SQLException {
        logger.debug("Trying to add elements {} to the table {}", elements, TABLE_NAME);

        for (Person person : elements) {
            add(person);
        }

        logger.debug("Added all elements to the table {}", TABLE_NAME);
    }
}
