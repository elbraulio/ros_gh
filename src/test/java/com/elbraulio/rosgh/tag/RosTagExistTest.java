package com.elbraulio.rosgh.tag;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.elbraulio.rosgh.tools.SqliteConnection;
import testtools.ResetDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosTagExistTest {
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
        id = new InsertRosTag(name).execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void exist() throws SQLException {
        assertTrue(id != -1);
        assertTrue(new RosTagExist(name).query(connection));
    }

    @Test
    public void notExist() throws SQLException {
        assertFalse(
                new RosTagExist(
                        String.valueOf(System.currentTimeMillis())
                ).query(connection)
        );
    }
}