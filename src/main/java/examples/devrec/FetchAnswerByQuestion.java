package examples.devrec;

import org.elbraulio.rosgh.question.Answer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchAnswerByQuestion {
    private final int id;
    private final Connection connection;

    public FetchAnswerByQuestion(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public List<Answer> list() throws SQLException {
        String userExists = "select ros_answer.* from ros_answer join " +
                "ros_question_answer rqa" +
                "                  on ros_answer.id = rqa.ros_answer_id where" +
                " rqa.ros_question_id = " + this.id + ";";
        List<Answer> answers = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                answers.add(
                        new Answer(
                                rs.getInt("id"),
                                rs.getInt("author"),
                                rs.getString("type"),
                                rs.getString("date"),
                                rs.getInt("votes"),
                                rs.getInt("is_accepted") == 1 ? true : false
                        )
                );
            }
        }
        return answers;
    }
}
