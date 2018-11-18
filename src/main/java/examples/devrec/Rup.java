package examples.devrec;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * create Rup association matrix filled with 0's.
 * ----p1-p2- ... -pn
 * |u1
 * |u2
 * |...
 * |un
 * then fill with 1's when a user collaborates in a repository.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class Rup implements Matrix {
    private final Connection connection;
    private final Map<Integer, Integer> projects;
    private final Map<Integer, Integer> users;
    private final Logger logger = Logger.getLogger(Matrix.class);

    public Rup(
            Connection connection, Map<Integer, Integer> projects,
            Map<Integer, Integer> users
    ) {

        this.connection = connection;
        this.projects = projects;
        this.users = users;
    }

    @Override
    public double[][] matrix() throws SQLException {
        double[][] rup = new double[users.size()][projects.size()];
        for (int userId : users.keySet()) {
            int userIndex = users.get(userId);
            this.logger.info("rup " + userIndex + " de " + rup.length);
            List<Integer> collaborations = new FetchIndexProjectsByUser(
                    userId, connection
            ).list();
            for (int projectId : collaborations) {
                int projectIndex = projects.get(projectId);
                rup[userIndex][projectIndex] = 1;
            }
        }
        return rup;
    }
}
