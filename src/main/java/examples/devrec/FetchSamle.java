package examples.devrec;

import org.elbraulio.rosgh.algorithm.TaggedItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchSamle {
    private final Connection connection;

    public FetchSamle(Connection connection) {

        this.connection = connection;
    }

    public List<TaggedItem> list() throws SQLException {
        String userExists = "select distinct rqt.ros_question_id " +
                "from ros_question_tag rqt " +
                "       join linked_tag_project ltp on rqt.ros_tag_id = ltp" +
                ".ros_tag_id " +
                "       join ros_question_answer rqa on rqt.ros_question_id =" +
                " rqa.ros_question_id " +
                "       join ros_answer a on rqa.ros_answer_id = a.id " +
                "where a.is_accepted == 1;";
        List<TaggedItem> questions = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                questions.add(
                        new FetchRosQuestion(rs.getInt("ros_question_id"),
                                connection).item()
                );
            }
        }
        return questions;
    }
}
