package org.elbraulio.rosgh.health;

import examples.devrec.FetchRosQuestion;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.tag.RosTag;
import org.elbraulio.rosgh.tag.Tag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchTagByUser {
    private final int id;
    private final Connection connection;

    public FetchTagByUser(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public List<Tag> list() throws SQLException {
        String userExists = "select distinct ros_tag.* from main.ros_tag join" +
                " ros_user_tag " +
                "rut " +
                " on ros_tag.id = rut.ros_tag_id join linked_users lu on rut" +
                ".ros_user_id=lu.ros_user_id where lu" +
                ".gh_user_id=" + this.id + ";";
        List<Tag> tags = new LinkedList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            while (rs.next()) {
                tags.add(
                        new RosTag(
                                rs.getString("name"),
                                rs.getInt("id")
                        )
                );
            }
        }
        return tags;
    }
}
