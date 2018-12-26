package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.health.LightAccuracy;
import org.elbraulio.rosgh.health.DefaultHealthCheck;
import org.elbraulio.rosgh.health.StrictAccuracy;
import org.elbraulio.rosgh.tools.SqliteConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class);

    public static void main(String... args)
            throws SQLException, ClassNotFoundException, IOException {
        try (
                Connection connection = new SqliteConnection(
                        "/Users/elbraulio/Google Drive/ros_gh/extractions/v1.1.db"
                ).connection()
        ) {
            long ti = System.currentTimeMillis();
            Map<Integer, Integer> users = new FetchIndexedUsers(
                    connection
            ).map();
            int questionId = 9033;
            TaggedItem question =
                    new FetchRosQuestion(questionId, connection).item();
            if (question == null){
                logger.error("question " + questionId + " not found");
                System.exit(1);
            }
            Map<Integer, Integer> tags = new FetchTags(connection).map();
            Algorithm devrec = new Devrec(
                    connection,
                    //new FetchIndexedProjects(connection).map(),
                    users,
                   //tags
                   new MatrixFromFile("ruuDA",
                    users.size()).matrix(),
                    new MatrixFromFile("ruuKA",
                            users.size()).matrix()
            );
            /*logger.info(
                    new ByRankAsc().orderedList(devrec.aspirants(question))
            );*/
            List<TaggedItem> sample =
                    new FetchSample(1, connection).list();
            logger.info("Light accuracy");
            new DefaultHealthCheck(new LightAccuracy(connection), devrec,
                    sample).logHealth();
            logger.info(
                    "process finished at " +
                            (System.currentTimeMillis() - ti) / 1000 + " seconds"
            );
        }
    }
}
