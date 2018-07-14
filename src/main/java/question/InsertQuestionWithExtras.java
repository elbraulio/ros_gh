package question;

import tag.InsertRosTag;
import tag.RosTagExist;
import tag.TagId;
import tools.SqlCommand;

import java.io.IOException;
import java.sql.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class InsertQuestionWithExtras implements SqlCommand {
    private final ApiRosQuestion api;
    private final RosDomQuestion dom;

    public InsertQuestionWithExtras(
            ApiRosQuestion api, RosDomQuestion dom
    ) {
        this.api = api;
        this.dom = dom;
    }

    @Override
    public int execute(Connection connection, int defaultValue)
            throws SQLException, IOException, InterruptedException {
        new InsertRosQuestion(this.api, this.dom.votes())
                .execute(connection, -1);
        for (String tag : this.api.tags()) {
            if (!new RosTagExist(tag).query(connection)) {
                final int tagId = new InsertRosTag(tag).execute(connection, -1);
                new InsertQuestionTag(this.api.id(), tagId)
                        .execute(connection, -1);
            } else {
                final int tagId = new TagId(tag).query(connection);
                new InsertQuestionTag(this.api.id(), tagId)
                        .execute(connection, -1);
            }
        }
        return 1;
    }
}
