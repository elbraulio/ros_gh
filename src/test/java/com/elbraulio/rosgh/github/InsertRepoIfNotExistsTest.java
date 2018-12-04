package com.elbraulio.rosgh.github;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.elbraulio.rosgh.tools.CanRequest;
import com.elbraulio.rosgh.tools.SqliteConnection;
import testtools.ResetDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertRepoIfNotExistsTest {
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
    public void insertNewRepo() throws SQLException, InterruptedException {
        assertTrue(
                new InsertRepoIfNotExists(
                        new DefaultGhRepo(
                                0, String.valueOf(System.currentTimeMillis()),
                                "elbraulio/ros_gh", 0, "system",
                                "source"
                        ), "source", 0, new CanRequest(60)
                ).execute(connection, -1) != -1
        );
    }

    @Test
    public void insertRepoThatExist() throws SQLException, InterruptedException {
        assertEquals(
                id,
                new InsertRepoIfNotExists(
                        new DefaultGhRepo(
                                0, name, "elbraulio/ros_gh", 0,
                                "system", "source"
                        ), "source", 0, new CanRequest(60)
                ).execute(connection, -1)
        );
    }
}