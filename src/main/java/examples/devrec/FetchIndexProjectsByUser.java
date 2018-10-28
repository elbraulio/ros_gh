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
public class FetchIndexProjectsByUser {
    private final int userId;
    private final Connection connection;

    public FetchIndexProjectsByUser(int userId, Connection connection) {
        this.userId = userId;
        this.connection = connection;
    }

    public List<Integer> list() throws SQLException {
        String userExists = "select distinct gh_commits.gh_repo_id as rid" +
                " from gh_commits" +
                " where gh_commits.gh_user_id = " + this.userId +
                " and gh_repo_id in" +
                "      (select linked_tag_project.gh_repo_id " +
                "      from linked_tag_project);";
        List<Integer> projects = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                projects.add(rs.getInt("rid"));
            }
        }
        return projects;
    }
}
