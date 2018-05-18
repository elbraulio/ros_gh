package resources;

import tag.Tag;
import tag.TagWithCount;
import user.RosDomUser;
import user.RosUser;

import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class SaveIntoSqliteDb {
    private final RosUser rosUser;
    private final Connection connection;

    public SaveIntoSqliteDb(final RosUser rosUser, Connection connection) {
        this.rosUser = rosUser;
        this.connection = connection;
    }

    public void execute() throws SQLException {
        // TODO: 16-05-18 refactor this temporal coupling
        // insert user
        String user = "INSERT INTO ros_user(name,up_votes,down_votes) " +
                "VALUES(?,?,?)";
        int userId = 0;

        try (PreparedStatement pstmt = this.connection.prepareStatement(user, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.rosUser.name());
            pstmt.setInt(2, this.rosUser.upVotes());
            pstmt.setInt(3, this.rosUser.downVotes());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()){
                userId=rs.getInt(1);
            }
        }

        // insert tags
        for (TagWithCount tag : this.rosUser.tags()) {
            String findTag = "SELECT id FROM ros_tag " +
                    "WHERE name='" + tag.name() + "'";
            int tagId = 0;
            // only if it exist
            try (
                    Statement stmt = this.connection.createStatement();
                    ResultSet rs = stmt.executeQuery(findTag)
            ) {
                if (rs.next()) {
                    tagId = rs.getInt(1);
                } else {
                    String insertTag = "INSERT INTO ros_tag(name) VALUES(?)";
                    try (PreparedStatement pstmt = this.connection
                            .prepareStatement(insertTag, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, tag.name());
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()){
                            tagId=rs1.getInt(1);
                        }
                    }
                }
            }
            // then join user with tag
            String join = "INSERT INTO ros_user_tag(ros_user_id,ros_tag_id," +
                    "count) VALUES (?,?,?)";
            try (PreparedStatement pstmt = this.connection.prepareStatement
                    (join)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, tagId);
                pstmt.setInt(3, tag.count());
                pstmt.executeUpdate();
            }
        }

        // insert interesting tags
        for (Tag tag : this.rosUser.interestingTags()) {
            String findTag = "SELECT id FROM ros_tag " +
                    "WHERE name='" + tag.name() + "'";
            int tagId = 0;
            // only if it exist
            try (
                    Statement stmt = this.connection.createStatement();
                    ResultSet rs = stmt.executeQuery(findTag)
            ) {
                if (rs.next()) {
                    tagId = rs.getInt(1);
                } else {
                    String insertTag = "INSERT INTO ros_tag(name) VALUES(?)";
                    try (PreparedStatement pstmt = this.connection
                            .prepareStatement(insertTag, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, tag.name());
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()){
                            tagId=rs1.getInt(1);
                        }
                    }
                }
            }
            // then join user with tag
            String join = "INSERT INTO ros_user_interesting_tag(ros_user_id," +
                    "ros_tag_id) VALUES (?,?)";
            try (PreparedStatement pstmt = this.connection.prepareStatement
                    (join)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, tagId);
                pstmt.executeUpdate();
            }
        }

        // insert ignored tags
        for (Tag tag : this.rosUser.ignoredTags()) {
            String findTag = "SELECT id FROM ros_tag " +
                    "WHERE name='" + tag.name() + "'";
            int tagId = 0;
            // only if it exist
            try (
                    Statement stmt = this.connection.createStatement();
                    ResultSet rs = stmt.executeQuery(findTag)
            ) {
                if (rs.next()) {
                    tagId = rs.getInt(1);
                } else {
                    String insertTag = "INSERT INTO ros_tag(name) VALUES(?)";
                    try (PreparedStatement pstmt = this.connection
                            .prepareStatement(insertTag, Statement.RETURN_GENERATED_KEYS)) {
                        pstmt.setString(1, tag.name());
                        pstmt.executeUpdate();
                        ResultSet rs1 = pstmt.getGeneratedKeys();
                        if (rs1.next()){
                            tagId=rs1.getInt(1);
                        }
                    }
                }
            }
            // then join user with tag
            String join = "INSERT INTO ros_user_ignored_tag(ros_user_id," +
                    "ros_tag_id) VALUES (?,?)";
            try (PreparedStatement pstmt = this.connection.prepareStatement
                    (join)) {
                pstmt.setInt(1, userId);
                pstmt.setInt(2, tagId);
                pstmt.executeUpdate();
            }
        }
    }
}
