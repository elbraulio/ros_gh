package com.elbraulio.rosgh.question;

import com.elbraulio.rosgh.tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertAnswer implements SqlCommand {
    private final Answer answer;

    public InsertAnswer(Answer answer) {
        this.answer = answer;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException {
        String user = "INSERT INTO ros_answer(" +
                "author," +
                "type," +
                "date," +
                "votes," +
                "is_accepted) VALUES(?,?,?,?,?)";

        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             user, Statement.RETURN_GENERATED_KEYS
                     )
        ) {
            int i = 1;
            pstmt.setInt(i++, this.answer.author());
            pstmt.setString(i++, this.answer.type());
            pstmt.setString(i++, this.answer.date());
            pstmt.setInt(i++, this.answer.votes());
            pstmt.setInt(i++, this.answer.isAccepted() ? 1 : 0);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return defaultValue;
    }
}
