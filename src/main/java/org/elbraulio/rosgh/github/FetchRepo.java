package org.elbraulio.rosgh.github;

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

    private final Connection connection;

    public FetchRepo(Connection connection) {

        this.connection = connection;
    }

    public List<GhRepo> list() throws SQLException {
        String userExists = "SELECT * FROM gh_repo;";
        List<GhRepo> ghRepos = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                ghRepos.add(
                        new DefaultGhRepo(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("full_name"),
                                rs.getInt("owner_id"),
                                rs.getString("description"),
                                rs.getString("source")
                        )
                );
            }
            return ghRepos;
        }
    }
}
