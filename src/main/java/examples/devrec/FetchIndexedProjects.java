package examples.devrec;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class FetchIndexedProjects {

    private final Connection connection;

    public FetchIndexedProjects(Connection connection) {

        this.connection = connection;
    }

    public Map<Integer, Integer> map() throws SQLException {
        String userExists = "SELECT distinct gh_repo_id FROM " +
                "linked_tag_project ORDER BY gh_repo_id;";
        Map<Integer, Integer> map = new HashMap<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            int index = 0;
            while (rs.next()) {
                map.put(rs.getInt("gh_repo_id"), index++);
            }
        }
        return map;
    }
}
