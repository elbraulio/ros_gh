package com.elbraulio.rosgh.github;

import com.elbraulio.rosgh.tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class InsertRepo implements SqlCommand {
    private final GhRepo ghRepo;
    private final String source;
    private final int ownerId;

    public InsertRepo(
            final GhRepo ghRepo, final String source, final int ownerId
    ) {
        this.ghRepo = ghRepo;
        this.source = source;
        this.ownerId = ownerId;
    }

    @Override
    public int execute(final Connection connection, final int defaultValue)
            throws SQLException {
        final String user = "INSERT INTO gh_repo(name,full_name,owner_id," +
                "description, source) VALUES(?,?,?,?,?)";
        try (
                PreparedStatement pstmt =
                        connection.prepareStatement(
                                user, Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            pstmt.setString(1, this.ghRepo.name());
            pstmt.setString(2, this.ghRepo.fullName());
            pstmt.setInt(3, ownerId);
            pstmt.setString(4, this.ghRepo.description());
            pstmt.setString(5, this.source);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return defaultValue;
            }
        }
    }
}
