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
public final class CheckTag implements CheckRelated {
    private final Connection connection;

    public CheckTag(Connection connection) {

        this.connection = connection;
    }

    @Override
    public List<Integer> isRelated(int relatedId) throws SQLException {
        String userExists = "select distinct gh_user_id " +
                "from ros_user_tag join linked_users " +
                "on ros_user_tag.ros_user_id = linked_users.ros_user_id " +
                "where ros_user_tag.ros_tag_id in (select ros_tag_id from " +
                "linked_tag_project) and" +
                "      ros_user_tag.ros_user_id in (select ros_user_id from " +
                "linked_users) and ros_tag_id=" + relatedId + ";";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            List<Integer> ids = new LinkedList<>();
            while (rs.next()) {
                ids.add(rs.getInt("gh_user_id"));
            }
            return ids;
        }
    }
}
