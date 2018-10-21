package org.elbraulio.rosgh.question;

import org.elbraulio.rosgh.tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertAnswerQuestion implements SqlCommand {
    private final int answerId;
    private final int questionId;

    public InsertAnswerQuestion(int answerId, int questionId) {
        this.answerId = answerId;
        this.questionId = questionId;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException {
        String user = "INSERT INTO ros_question_answer(" +
                "ros_question_id," +
                "ros_answer_id) VALUES(?,?)";

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             user, Statement.RETURN_GENERATED_KEYS
                     )
        ) {
            int i = 1;
            pstmt.setInt(i++, this.questionId);
            pstmt.setInt(i++, this.answerId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return defaultValue;
    }
}
