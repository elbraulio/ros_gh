package org.elbraulio.rosgh.health;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FecthGhUserAccepted {
    private final int questionId;
    private final Connection connection;

    public FecthGhUserAccepted(int questionId, Connection connection) {
        this.questionId = questionId;
        this.connection = connection;
    }

    public int id() throws SQLException {
        String userExists = "select ra.author " +
                "from ros_answer ra " +
                "       join ros_question_answer rqa on ra.id = rqa" +
                ".ros_answer_id " +
                "where rqa.ros_question_id=" + this.questionId +
                " and ra.is_accepted=1;";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            if (rs.next()) return rs.getInt("author");
        }
        return 0;
    }
}
