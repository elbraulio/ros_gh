package question;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tools.SqliteConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertQuestionTagTest {
    private Connection connection;
    private int id;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException,
            IOException, InterruptedException {
        connection = new SqliteConnection("src/test/java/resources/sqlite/test.db")
                .connection();
        id = new InsertQuestionTag(0,0)
                .execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void ok(){
        assertTrue(id != -1);
    }
}