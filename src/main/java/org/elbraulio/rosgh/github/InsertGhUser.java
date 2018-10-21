package org.elbraulio.rosgh.github;

import org.elbraulio.rosgh.tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertGhUser implements SqlCommand {
    private final GhUser ghUser;

    public InsertGhUser(GhUser ghUser) {
        this.ghUser = ghUser;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException {
        String user = "INSERT INTO gh_user(" +
                "login," +
                "followers," +
                "url," +
                "name," +
                "company," +
                "email) VALUES(?,?,?,?,?,?)";

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             user, Statement.RETURN_GENERATED_KEYS
                     )
        ) {
            pstmt.setString(1, this.ghUser.login());
            pstmt.setInt(2, this.ghUser.followers());
            pstmt.setString(3, this.ghUser.url());
            pstmt.setString(4, this.ghUser.name());
            pstmt.setString(5, this.ghUser.company());
            pstmt.setString(6, this.ghUser.email());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return defaultValue;
    }
}
