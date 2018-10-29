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
public final class Devrec implements Algorithm {

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
             * then fill with 1's when a user collaborates in a repository.
             */
            double[][] rup = new double[users.size()][projects.size()];
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
            /*
             * create Ruu association matrix filled with 0's. This matrix is
             * symmetric.
             */
            double[][] ruu = new double[users.size()][users.size()];
            for (int i = 0; i < ruu.length; i++) {
                for (int j = i; j < ruu.length; j++) {
                    ruu[i][j] = jaccardSimilarity(i, j, rup);
                    ruu[j][i] = jaccardSimilarity(i, j, rup);
                }
            }
            this.logger.info("Ruu matrix:\n" + printMatrix(ruu));
            /*
             * create KA rank for each user.
             */
            for (int i = 0; i < ruu.length; i++) {
                aspirants.add(
                        new DvrecAspirant(ka(i, projects.get(513), ruu, rup))
                );
            }
        } catch (Exception e) {
            this.logger.error("e", e);
        }
        return aspirants;
    }

    private double ka(
            int userIndex, int projectIndex, double[][] ruu, double[][] rup
    ) {
        double ka = 0d;
        for (int i = 0; i < rup.length; i++) {
            if (rup[i][projectIndex] == 1) {
                ka += ruu[userIndex][i];
            }
        }
        return ka;
    }

    /**
     * Compare similarity between user i toward user j.
     *
     * @param i   user to compared
     * @param j   user to compare with
     * @param rup user project relation matrix.
     * @return jaccard index between user i and j.
     */
    private double jaccardSimilarity(int i, int j, double[][] rup) {
        double[] projectsI = rup[i];
        double[] projectsJ = rup[j];
        double sum = 0d;
        double sampleI = 0d;
        double sampleJ = 0d;
        for (int k = 0; k < projectsI.length; k++) {
            sum += projectsI[k] + projectsJ[k] == 2d ? 1 : 0;
            sampleI += projectsI[k];
            sampleJ += projectsJ[k];
        }
        if (sampleI + sampleJ == 0) {
            return 0;
        } else {
            return sum / (sampleI + sampleJ);
        }
    }

    /**
     * print a matrix.
     *
     * @param matrix to be printed.
     * @return string representing the matrix.
     */
    private String printMatrix(double[][] matrix) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            double[] row = matrix[i];
            for (int j = 0; j < row.length; j++) {
                builder.append(row[j]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    final class DvrecAspirant implements Aspirant {

        private final double ka;

        public DvrecAspirant(double ka) {

            this.ka = ka;
        }

        @Override
        public double rank() {
            return this.ka;
        }

        @Override
        public String toString() {
            return String.valueOf(this.ka);
        }
    }
}
