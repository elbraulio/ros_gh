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
        String tagQuery = "SELECT MIN(ltp.gh_repo_id) as gh_repo_id,\n" +
                "       ltp.ros_tag_id\n" +
                "FROM linked_tag_project ltp\n" +
                "       JOIN (SELECT ltp2.ros_tag_id,\n" +
                "                    MAX(ltp2.gh_repo_id) AS max_repo\n" +
                "             FROM linked_tag_project ltp2\n" +
                "             GROUP BY ltp2.ros_tag_id) y ON y.ros_tag_id = ltp.ros_tag_id\n" +
                "    and y.max_repo=ltp.gh_repo_id\n" +
                "join ros_question_tag rqt on ltp.ros_tag_id=rqt.ros_tag_id\n" +
                "where rqt.ros_question_id = " + this.id + "\n" +
                "GROUP BY ltp.ros_tag_id, ltp.gh_repo_id;";
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
