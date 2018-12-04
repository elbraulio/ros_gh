package com.elbraulio.rosgh.github;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.elbraulio.rosgh.tools.SqliteConnection;
import testtools.ResetDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhRepoByNameTest {

    private Connection connection;
    private int id;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException,
            IOException, InterruptedException {
        new ResetDb().reset();
        connection = new SqliteConnection("src/test/resources/sqlite/test.db")
                .connection();
        id = new InsertRepo(
                new DefaultGhRepo(
                        0, "ros_gh", "elbraulio/ros_gh",
                        0, "system", "source"
                ), "source", 0
        ).execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void simple() throws SQLException, ClassNotFoundException {
        DefaultGhRepo repo = new GhRepoByName("ros_gh").query(connection);
        assertTrue(id != -1);
    }
}