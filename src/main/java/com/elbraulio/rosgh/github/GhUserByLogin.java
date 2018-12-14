package com.elbraulio.rosgh.github;

import com.elbraulio.rosgh.tools.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUserByLogin implements SqlQuery<GhUser> {
    private final String owner;

    public GhUserByLogin(String owner) {

        this.owner = owner;
    }

    @Override
    public GhUser query(Connection connection) throws SQLException {
        String userExists = "SELECT * FROM gh_user " +
                "WHERE login='" + this.owner + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            if(rs.next()) {
                return new GhUser(
                        rs.getInt("id"),
                        rs.getString("login"),
                        rs.getInt("followers"),
                        rs.getString("url"),
                        rs.getString("name"),
                        rs.getString("company"),
                        rs.getString("email")
                );
            } else {
                // TODO: 22-09-18 remove this null
                return null;
            }
        }
    }
}
