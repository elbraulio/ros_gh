package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.Question;
import org.elbraulio.rosgh.tools.SqliteConnection;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Devrec implements Algorithm {

    private final Logger logger = Logger.getLogger(Devrec.class);

    @Override
    public List<Aspirant> aspirants(Question question) {
        List<Aspirant> aspirants = new LinkedList<>();
        try (
                Connection connection = new SqliteConnection(
                        "/Users/elbraulio/Documents/v1.0.db"
                ).connection()
        ) {
            /*
             * <id, matrix index>, both int are in asc order and matrix index
             * starts from 0.
             */
            Map<Integer, Integer> projects = new FetchIndexedProjects(
                    connection
            ).map();
            Map<Integer, Integer> users = new FetchIndexedUsers(
                    connection
            ).map();

            /*
             * create Rup association matrix filled with 0's.
             * ----p1-p2- ... -pn
             * |u1
             * |u2
             * |...
             * |un
             */
            int[][] rup = new int[users.size()][projects.size()];
            for (int userId : users.keySet()) {
                int userIndex = users.get(userId);
                List<Integer> collaborations = new FetchIndexProjectsByUser(
                        userId, connection
                ).list();
                for (int projectId : collaborations) {
                    int projectIndex = projects.get(projectId);
                    rup[userIndex][projectIndex] = 1;
                }
            }
            this.logger.info("Rup matrix:\n" + printMatrix(rup));
        } catch (Exception e) {
            this.logger.error("e", e);
        }
        return aspirants;
    }

    private String printMatrix(int[][] matrix) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            int[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                builder.append(row[j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
