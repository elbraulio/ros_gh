package examples.devrec;

import org.elbraulio.rosgh.algorithm.TaggedItem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchRosQuestion {
    private final int id;
    private final Connection connection;

    public FetchRosQuestion(int id, Connection connection) {
        this.id = id;
        this.connection = connection;
    }

    public TaggedItem item() throws SQLException {
        String sql = "select * from ros_question where id = " + this.id + ";";
        try(Statement st = this.connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            if(rs.next()) {
                return new TaggedQuestion(
                        new FetchRepo(this.id, connection).repos(),
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("author"),
                        rs.getLong("added_at"),
                        rs.getString("summary"),
                        rs.getString("url"),
                        rs.getInt("last_activity_by"),
                        rs.getInt("view_count"),
                        rs.getInt("votes"),
                        new FetchTagsByQuestion(this.id, connection).list(),
                        new FetchAnswerByQuestion(this.id, connection).list()
                );
            }
        }
        return null;
    }
}
