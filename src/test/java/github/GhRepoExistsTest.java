package github;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tools.SqliteConnection;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhRepoExistsTest {

    private Connection connection;
    private int id;
    private String name;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException {
        name = String.valueOf(System.currentTimeMillis());
        connection = new SqliteConnection("src/test/java/resources/sqlite/test.db")
                .connection();
        id = new InsertRepo(
                new DefaultGhRepo(
                        0, name, "elbraulio/ros_gh",
                        0, "system", "source"
                ), "source", 0
        ).execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void exist() throws SQLException {
        assertTrue(id != -1);
        assertTrue(new GhRepoExists(name).query(connection));
    }

    @Test
    public void notExist() throws SQLException {
        assertFalse(
                new GhRepoExists(String.valueOf(System.currentTimeMillis()))
                .query(connection)
        );
    }
}