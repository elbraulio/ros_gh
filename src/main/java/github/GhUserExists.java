package github;

import tools.SqlQuery;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUserExists implements SqlQuery<Boolean> {
    private final String owner;

    public GhUserExists(String owner) {

        this.owner = owner;
    }

    @Override
    public Boolean query(Connection connection) throws SQLException {
        String userExists = "SELECT * FROM gh_user " +
                "WHERE login='" + this.owner + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            return rs.next();
        }
    }
}
