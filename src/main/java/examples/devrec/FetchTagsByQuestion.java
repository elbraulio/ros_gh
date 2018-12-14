package examples.devrec;

import com.elbraulio.rosgh.tag.RosTag;
import com.elbraulio.rosgh.tag.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchTagsByQuestion {
    private final int id;
    private final Connection connection;

    public FetchTagsByQuestion(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public List<Tag> list() throws SQLException {
        String tagQuery = "select ros_tag.* from ros_tag join " +
                "ros_question_tag " +
                "                  on ros_tag.id = ros_question_tag.ros_tag_id where " +
                "    ros_question_tag.ros_question_id = " + this.id + "; ";
        List<Tag> tags = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(tagQuery)
        ) {
            while (rs.next()) {
                tags.add(new RosTag(rs.getString("name"),
                        rs.getInt("id")));
            }
        }
        return tags;
    }
}
