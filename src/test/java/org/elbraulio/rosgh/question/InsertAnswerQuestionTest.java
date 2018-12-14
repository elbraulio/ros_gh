package org.elbraulio.rosgh.question;

import org.elbraulio.rosgh.tools.SqliteConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testtools.ResetDb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertAnswerQuestionTest {
    private Connection connection;
    private int id;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException,
            IOException, InterruptedException {
        new ResetDb().reset();
        connection = new SqliteConnection("src/test/resources/sqlite/test.db")
                .connection();
        id = new InsertAnswerQuestion(0,0)
                .execute(connection, -1);
    }

    @After
    public void close() throws SQLException {
        connection.close();
    }

    @Test
    public void exist(){
        assertTrue(id != -1);
    }
}