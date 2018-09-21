package question;

import tools.SqlCommand;

import java.io.IOException;
import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertQuestionTag implements SqlCommand {
    private final int questionId;
    private final int tagId;

    public InsertQuestionTag(int questionId, int tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException, IOException, InterruptedException {
        final String user = "INSERT INTO ros_question_tag(" +
                "ros_question_id," +
                "ros_tag_id) VALUES(?,?)";
        try (
                PreparedStatement pstmt =
                        connection.prepareStatement(
                                user, Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            pstmt.setInt(1, this.questionId);
            pstmt.setInt(2, this.tagId);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            return 0;
        }
    }
}
