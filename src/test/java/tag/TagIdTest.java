package tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tools.SqliteConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagIdTest {
    private Connection connection;
    private int id;
    private String name;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException {
        name = String.valueOf(System.currentTimeMillis());
        connection = new SqliteConnection("src/test/java/resources/sqlite/test.db")
                .connection();
        id = new InsertRosTag(name).execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void ok() throws SQLException {
        assertEquals(
                id,
                new TagId(name).query(connection).intValue()
                );
    }
}