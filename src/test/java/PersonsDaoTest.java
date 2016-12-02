import com.github.aarexer.crud.DbConnection;
import com.github.aarexer.crud.dao.PersonsDao;
import com.github.aarexer.crud.model.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;
import static org.junit.Assert.*;

public class PersonsDaoTest {
    private static PersonsDao personsDao;

    @BeforeClass
    public static void beforeAllTests() {
        personsDao = new PersonsDao(DbConnection.getConnection());
    }

    @AfterClass
    public static void afterAllTests() throws SQLException {
        DbConnection.closeConnection();
    }

    @Test
    public void getPersonFromTablePersonsById() throws SQLException{
        Optional<Person> personFromDb = personsDao.get(1);

        assertTrue(personFromDb.isPresent());
        assertEquals("Testing", personFromDb.get().getName());
        assertEquals("888", personFromDb.get().getPhone());
        assertEquals(1, personFromDb.get().getId());
    }

    @Test
    public void clearPersonsTable() throws SQLException {
        personsDao.clear();

        assertEquals(true, personsDao.getAll().isEmpty());
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
