package examples.devrec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchTagCount {

    private final String whereUser;
    private final String whereTag;
    private final Connection connection;

    public FetchTagCount(Connection connection) {
        this("1", "1", connection);
    }

    public FetchTagCount(int tagId, int ghUserId, Connection connection) {
        this(
                "gh_user_id=" + ghUserId,
                "ros_tag_id=" + tagId,
                connection
        );
    }

    public FetchTagCount(
            String whereUser, String whereTag, Connection connection
    ) {
        this.whereUser = whereUser;
        this.whereTag = whereTag;
        this.connection = connection;
    }

    public int count() throws SQLException {
        String userExists = "select sum(count) as count " +
                "from ros_user_tag " +
                "where ros_user_id in " +
                "(select ros_user_id from linked_users where " +
                this.whereUser + ") and " + this.whereTag + ";";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        }
        return 0;
    }
}
