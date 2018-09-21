package tag;

import github.GhUser;
import tools.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagId implements SqlQuery<Integer> {
    private final String tag;

    public TagId(String tag) {
        this.tag = tag;
    }

    @Override
    public Integer query(Connection connection) throws SQLException {
        String userExists = "SELECT * FROM ros_tag " +
                "WHERE name='" + this.tag + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            return rs.getInt("id");
        }
    }
}
