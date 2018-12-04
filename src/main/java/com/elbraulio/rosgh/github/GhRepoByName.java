package com.elbraulio.rosgh.github;

import com.elbraulio.rosgh.tools.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhRepoByName implements SqlQuery<DefaultGhRepo> {
    private final String name;

    public GhRepoByName(String name) {
        this.name = name;
    }

    @Override
    public DefaultGhRepo query(Connection connection) throws SQLException {
        String userExists = "SELECT * FROM gh_repo " +
                "WHERE name='" + this.name + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            return new DefaultGhRepo(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("full_name"),
                    rs.getInt("owner_id"),
                    rs.getString("description"),
                    rs.getString("source")
            );
        }
    }
}
