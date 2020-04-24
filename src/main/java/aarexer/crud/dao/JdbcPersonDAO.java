package aarexer.crud.dao;

import aarexer.crud.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public final class JdbcPersonDAO implements PersonDAO {
    private static final Logger logger = LogManager.getLogger();

    private final DataSource dataSource;

    public JdbcPersonDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void init() {
        logger.info("Trying to create table {}", TABLE_NAME);

        try (final Connection connection = dataSource.getConnection();
             final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE);

            logger.info("Table {} was created.", TABLE_NAME);
        } catch (SQLException e) {
            throw new DAOException("Error while creating table " + TABLE_NAME, e);
        }
    }

    @Override
    public void add(Person person) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(INSERT)) {
            logger.debug("Adding {} to database", person);

            statement.setLong(1, person.getId());
            statement.setString(2, person.getName());
            statement.setString(3, person.getPhone());

            statement.executeUpdate();

            logger.debug("Person {} added to {} table", person, TABLE_NAME);
        } catch (SQLException e) {
            throw new DAOException("Error while adding element", e);
        }
    }

    @Override
    public void remove(Person person) {
        removeById(person.getId());
    }

    @Override
    public void removeById(long id) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(REMOVE_BY_ID)) {
            logger.debug("Remove person by id: {}", id);

            statement.setLong(1, id);

            if (statement.executeUpdate() > 0) {
                logger.debug("Element with id {} removed from {} table", id, TABLE_NAME);
            } else {
                logger.error("Can't remove element with id {} from {} table", id, TABLE_NAME);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while removing element", e);
        }
    }

    @Override
    public Optional<Person> findById(final long id) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            logger.debug("Get person by id: {}", id);

            statement.setLong(1, id);

            try (final ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    final String name = res.getString("name");
                    final String phone = res.getString("phone");

                    logger.debug("Person with id: {} returned", id);

                    return Optional.of(new Person(id, name, phone));
                } else {
                    logger.warn("Person with id: {} doesn't exist", id);
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error while finding element", e);
        }
    }

    @Override
    public List<Person> findAll() {
        logger.debug("Trying to get all elements from table: {}", TABLE_NAME);

        try (final Connection connection = dataSource.getConnection();
             final Statement stmt = connection.createStatement();
             final ResultSet rs = stmt.executeQuery(FIND_ALL)) {

            final List<Person> people = new ArrayList<>();

            while (rs.next()) {
                final Long id = rs.getLong("id");
                final String name = rs.getString("name");
                final String phone = rs.getString("phone");

                people.add(new Person(id, name, phone));
            }

            if (people.size() > 0) {
                logger.debug("Return {} rows after get all query for table {}", people.size(), TABLE_NAME);
            } else {
                logger.debug("Empty list after get all query for table {}", TABLE_NAME);
            }

            return people;
        } catch (SQLException e) {
            throw new DAOException("Error while executing findAll", e);
        }
    }

    @Override
    public void removeAll() {
        logger.debug("Trying to clear table {}", TABLE_NAME);

        try (final Connection connection = dataSource.getConnection();
             final Statement statement = connection.createStatement()) {
            final int numOfRows = statement.executeUpdate(CLEAR_TABLE);

            logger.debug("Table {} cleared, removed {} rows.", TABLE_NAME, numOfRows);
        } catch (SQLException e) {
            throw new DAOException("Error while executing removeAll", e);
        }
    }

    @Override
    public void addAll(final List<Person> elements) {
        logger.debug("Trying to add elements {} to the table {}", elements, TABLE_NAME);

        for (Person person : elements) {
            add(person);
        }

        logger.debug("Added all elements: {}, to the table {}", elements, TABLE_NAME);
    }
}
