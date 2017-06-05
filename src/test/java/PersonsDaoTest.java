import com.github.aarexer.crud.DbConnection;
import com.github.aarexer.crud.dao.JdbcPersonsDao;
import com.github.aarexer.crud.model.Person;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonsDaoTest {
    private static JdbcPersonsDao personsDao;

    @BeforeClass
    public static void beforeAllTests() {
        personsDao = new JdbcPersonsDao(DbConnection.getConnection());
    }

    @AfterClass
    public static void afterAllTests() throws SQLException {
        DbConnection.closeConnection();
    }

    @Before
    public void setUp() throws Exception {
        personsDao.clear();
    }

    @Test
    public void getPersonFromTablePersonsById() throws SQLException {
        Person personForTest = new Person(1, "Testing", "888");
        personsDao.add(personForTest);

        Optional<Person> personFromDb = personsDao.get(1);

        assertTrue(personFromDb.isPresent());
        assertEquals("Testing", personFromDb.get().getName());
        assertEquals("888", personFromDb.get().getPhone());
        assertEquals(1, personFromDb.get().getId());
    }

    @Test
    public void addPersonToTablePersons() throws SQLException {
        Person personForTest = new Person(1, "Testing", "888");
        personsDao.add(personForTest);
    }

    @Test
    public void getAllPersonsFromTable() throws SQLException {
        Person personForTest = new Person(1, "Testing", "888");
        personsDao.add(personForTest);

        assertEquals(false, personsDao.getAll().isEmpty());
    }
}
