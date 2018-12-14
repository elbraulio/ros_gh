package examples.devrec;

import org.apache.log4j.Logger;
import com.elbraulio.rosgh.algorithm.TaggedItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchSample {
    private final Connection connection;
    private final Logger logger = Logger.getLogger(FetchSample.class);
    private final String size;

    public FetchSample(int size, Connection connection) {
        this(" limit " + size + " ", connection);
    }

    private FetchSample(String size, Connection connection) {

        this.size = size;
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
                " join linked_users lu on lu.ros_user_id=a.author " +
                "where a.is_accepted == 1 " + this.size + " ;";
        List<TaggedItem> questions = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                this.logger.info("Sample question id " + rs.getInt("ros_question_id"));
                questions.add(
                        new FetchRosQuestion(rs.getInt("ros_question_id"),
                                connection).item()
                );
            }
        }
        return questions;
    }
}
