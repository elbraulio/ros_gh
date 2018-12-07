package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.ByRankDesc;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.health.HealthCheck;
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
                        "/Users/elbraulio/Documents/v1.1.db"
                ).connection()
        ) {
            long ti = System.currentTimeMillis();
            Map<Integer, Integer> users = new FetchIndexedUsers(
                    connection
            ).map();
            int questionId = 9039;
            TaggedItem question =
                    new FetchRosQuestion(questionId, connection).item();
            if (question == null){
                logger.error("question " + questionId + " not found");
                System.exit(1);
            }
            Algorithm devrec = new Devrec(
                    connection,
                    users,
                    new MatrixFromFile("/Users/elbraulio/Desktop/ruu_da.v2", users.size()).matrix(),
                    new MatrixFromFile("/Users/elbraulio/Desktop/ruu_ka.v2", users.size()).matrix()
            );
            List<Aspirant> sorted = new ByRankDesc().orderedList(
                    devrec.aspirants(question)
            );

            logger.info(
                    "process finished at " +
                            (System.currentTimeMillis() - ti) / 1000 + " seconds"
            );
            logger.info(sorted);
        }
    }
}
