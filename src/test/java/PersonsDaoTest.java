import com.github.aarexer.crud.DbConnection;
import com.github.aarexer.crud.dao.PersonsDao;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class PersonsDaoTest {
    private PersonsDao personsDao;

    @BeforeClass
    void setup() {
        personsDao = new PersonsDao(DbConnection.getInstance().getConnection());
    }

    @Test
    public void createDataBaseTest() throws SQLException {
        personsDao.createTable();

    }
}
