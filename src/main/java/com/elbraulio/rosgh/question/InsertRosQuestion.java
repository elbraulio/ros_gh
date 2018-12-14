package com.elbraulio.rosgh.question;

import com.elbraulio.rosgh.tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertRosQuestion implements SqlCommand {
    private final ApiRosQuestion api;
    private final int votes;

    public InsertRosQuestion(ApiRosQuestion api, int votes) {
        this.api = api;
        this.votes = votes;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException {
        final String user = "INSERT INTO ros_question(" +
                "id," +
                "title," +
                "author," +
                "added_at," +
                "summary," +
                "url," +
                "last_activity_at," +
                "last_activity_by," +
                "view_count," +
                "votes) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (
                PreparedStatement pstmt =
                        connection.prepareStatement(
                                user, Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            int i = 1;
            pstmt.setInt(i++, this.api.id());
            pstmt.setString(i++, this.api.title());
            pstmt.setInt(i++, this.api.author());
            pstmt.setString(i++, this.api.addedAt());
            pstmt.setString(i++, this.api.summary());
            pstmt.setString(i++, this.api.url());
            pstmt.setString(i++, this.api.lastActivityAt());
            pstmt.setInt(i++, this.api.lastActivityBy());
            pstmt.setInt(i++, this.api.viewCount());
            pstmt.setInt(i++, this.votes);
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
