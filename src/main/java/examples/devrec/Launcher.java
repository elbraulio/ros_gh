package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.ByRankAsc;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.health.DefaultAccuracy;
import org.elbraulio.rosgh.health.DefaultHealthCheck;
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
                        "C:\\Users\\Usuario\\Desktop\\v1.1.db"
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
                   new MatrixFromFile("C:\\Users\\Usuario\\Desktop\\ruuDA",
                    users.size()).matrix(),
                    new MatrixFromFile("C:\\Users\\Usuario\\Desktop\\ruuKA",
                            users.size()).matrix()
            );
            logger.info(
                    new ByRankAsc().orderedList(devrec.aspirants(question))
            );
            List<TaggedItem> sample =
                    new FetchSample(1, connection).list();
            new DefaultHealthCheck(new DefaultAccuracy(connection), devrec,
                    sample).logHealth();
            logger.info(
                    "process finished at " +
                            (System.currentTimeMillis() - ti) / 1000 + " seconds"
            );
        }
    }
}
