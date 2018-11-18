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
public final class CheckProject implements CheckRelated {
    private final Connection connection;

    public CheckProject(Connection connection) {

        this.connection = connection;
    }

    @Override
    public List<Integer> isRelated(int relatedId) throws SQLException {
        String userExists = "select distinct gh_user_id " +
                "from gh_commits " +
                "where gh_repo_id in " +
                "(SELECT gh_repo_id FROM linked_tag_project) and gh_repo_id = " +
                relatedId + ";";
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
