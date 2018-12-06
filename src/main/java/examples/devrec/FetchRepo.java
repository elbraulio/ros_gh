package examples.devrec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchRepo {
    private final int id;
    private final Connection connection;

    public FetchRepo(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public List<Integer> repos() throws SQLException {
        String tagQuery = "select distinct ltp.* " +
                "from linked_tag_project ltp join ros_question_tag rqt on  " +
                "ltp.ros_tag_id = rqt.ros_tag_id " +
                "where rqt.ros_question_id=" + this.id + "; ";
        List<Integer> ids = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(tagQuery)
        ) {
            while (rs.next()) {
                ids.add(rs.getInt("gh_repo_id"));
            }
        }
        return ids;
    }
}
