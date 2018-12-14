package com.elbraulio.rosgh.question;

import com.elbraulio.rosgh.tools.SqliteConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import testtools.ResetDb;

import javax.json.Json;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertRosQuestionTest {
    private Connection connection;
    private int id;

    @Before
    public void insertRepo() throws SQLException, ClassNotFoundException,
            IOException, InterruptedException {
        new ResetDb().reset();
        connection = new SqliteConnection("src/test/resources/sqlite/test.db")
                .connection();
        id = new InsertRosQuestion(
                new DefaultApiRosQuestion(
                        Json.createReader(
                                // TODO: 22-09-18 from resurces
                                new StringReader(
                                        "{\"tags\": [\"hardware\", \"drivers\"], " +
                                                "\"answer_count\": 2, \"accepted" +
                                                "_answer_id\": 13036, \"answer_ids\"" +
                                                ": [13031, 13036], \"id\": 9033," +
                                                " \"last_activity_by\": {\"usern" +
                                                "ame\": \"tfoote\", \"id\": 3}," +
                                                " \"view_count\": 690, \"last_ac" +
                                                "tivity_at\": \"1297774294\", \"" +
                                                "title\": \"what hardware driver" +
                                                "s does ROS provide?\", \"url\":" +
                                                " \"http://answers.ros.org/ques" +
                                                "tion/9033/what-hardware-driver" +
                                                "s-does-ros-provide/\", \"autho" +
                                                "r\": {\"username\": \"mmwise\"" +
                                                ", \"id\": 2}, \"added_at\": \"" +
                                                "1297650523\", \"summary\": \"<p" +
                                                ">A list of stacks, packages, or" +
                                                " tutorials related to hardware " +
                                                "drivers.</p>\\n\", \"score\": " +
                                                "2}"
                                )
                        ).readObject()
                )
                ,0
        )
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