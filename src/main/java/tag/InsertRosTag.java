package tag;

import tools.SqlCommand;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertRosTag implements SqlCommand {
    private final String tag;

    public InsertRosTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException {
        String insertTag = "INSERT INTO ros_tag(name) VALUES(?)";
        try (PreparedStatement pstmt = connection
                .prepareStatement(insertTag, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.tag);
            pstmt.executeUpdate();
            ResultSet rs1 = pstmt.getGeneratedKeys();
            if (rs1.next()) {
                return rs1.getInt(1);
            } else {
                return defaultValue;
            }
        }
    }
}
