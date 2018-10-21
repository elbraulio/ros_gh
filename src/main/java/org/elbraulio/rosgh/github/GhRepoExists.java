package org.elbraulio.rosgh.github;

import org.elbraulio.rosgh.tools.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhRepoExists implements SqlQuery<Boolean> {
    private final String name;

    public GhRepoExists(String name) {
        this.name = name;
    }

    @Override
    public Boolean query(Connection connection) throws SQLException {
        String repoExists = "SELECT * FROM gh_repo " +
                "WHERE name='" + this.name + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(repoExists)
        ) {
            return rs.next();
        }
    }
}
