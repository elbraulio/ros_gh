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
public final class RelatedTags {
    private final Connection connection;

    public RelatedTags(Connection connection) {
        this.connection = connection;
    }

    public List<Integer> isRelated(int relatedId) throws SQLException {
        String userExists = "select distinct ros_tag_id " +
                "from ros_user_tag " +
                "where ros_user_id in " +
                "      (select ros_user_id from linked_users where " +
                "gh_user_id=" + relatedId + ") " +
                "  and ros_tag_id in (select distinct ros_tag_id from " +
                "linked_tag_project) " +
                "  and 1;";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            List<Integer> ids = new LinkedList<>();
            while (rs.next()) {
                ids.add(rs.getInt("ros_tag_id"));
            }
            return ids;
        }
    }
}
