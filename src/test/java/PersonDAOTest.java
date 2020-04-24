import aarexer.crud.dao.JdbcPersonDAO;
import aarexer.crud.model.Person;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sqlite.SQLiteDataSource;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PersonDAOTest {
    private static JdbcPersonDAO personsDao;

    @BeforeClass
    public static void beforeAllTests() {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite::resource:crud.db");
        personsDao = new JdbcPersonDAO(sqLiteDataSource);
        personsDao.init();
    }

    @Before
    public void setUp() {
        personsDao.removeAll();
    }

    @Test
    public void findByExistingIdTest() {
        Person personForTest = new Person(1L, "Testing", "888");
        personsDao.add(personForTest);

        Optional<Person> personFromDb = personsDao.findById(1L);

        assertTrue(personFromDb.isPresent());
        assertEquals("Testing", personFromDb.get().getName());
        assertEquals("888", personFromDb.get().getPhone());
        assertEquals(Long.valueOf(1), personFromDb.get().getId());
    }

    @Test
    public void addPersonTest() {
        Person personForTest = new Person(1L, "Testing", "888");
        personsDao.add(personForTest);
    }

    @Test
    public void removePersonByIdTest() {
        Person personForTest = new Person(1L, "Testing", "888");
        personsDao.add(personForTest);

        Optional<Person> personFromDb = personsDao.findById(1L);

        assertTrue(personFromDb.isPresent());

        personsDao.removeById(1L);

        Optional<Person> removed = personsDao.findById(1L);
        assertFalse(removed.isPresent());
    }

    @Test
    public void findAllPersonsTest() {
        Person personForTest = new Person(1L, "Testing", "888");
        personsDao.add(personForTest);

        List<Person> persons = personsDao.findAll();
        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
    }
}
