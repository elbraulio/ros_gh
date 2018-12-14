package com.elbraulio.rosgh.github;

import com.elbraulio.rosgh.tools.SqliteConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testtools.ResetDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUserByLoginTest {
    private Connection connection;
    private int id;
    private String name;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException,
            IOException, InterruptedException {
        new ResetDb().reset();
        name = String.valueOf(System.currentTimeMillis());
        connection = new SqliteConnection("src/test/resources/sqlite/test.db")
                .connection();
        id = new InsertGhUser(
                new GhUser(
                        0, name, 0,
                        "https://github.com/elbraulio", "Braulio",
                        "", "brauliop.3@gmail.com"
                )
        ).execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void sql() throws SQLException {
        GhUser user = new GhUserByLogin(name).query(connection);
        assertTrue(id != -1);
        assertNotNull(user != null);
        assertTrue(user.login().equals(name));
    }
}