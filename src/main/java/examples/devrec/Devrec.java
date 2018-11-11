package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.Question;
import org.elbraulio.rosgh.tools.SqliteConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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
                this.logger.info("rup " + userIndex + " de " + rup.length);
                List<Integer> collaborations = new FetchIndexProjectsByUser(
                        userId, connection
                ).list();
                for (int projectId : collaborations) {
                    int projectIndex = projects.get(projectId);
                    rup[userIndex][projectIndex] = 1;
                }
            }
            /*
             * create Ruu association matrix filled with 0's. This matrix is
             * symmetric.
             */
            double[][] ruuDA = new double[users.size()][users.size()];
            for (int i = 0; i < ruuDA.length; i++) {
                this.logger.info("ruuDA " + i + " de " + ruuDA.length);
                for (int j = i; j < ruuDA.length; j++) {
                    ruuDA[i][j] = jaccardSimilarity(i, j, rup);
                    ruuDA[j][i] = jaccardSimilarity(i, j, rup);
                }
            }
            /*
             * create a map with all related tags. <id, matrix index>, both int
             * are in asc order and matrix index starts from 0.
             */
            Map<Integer, Integer> tags = new FetchTags(connection).map();
            double allCountSum = new FetchTagCount(connection).count();
            double[][] rut = new double[users.size()][tags.size()];
            for (int userId : users.keySet()) {
                int userIndex = users.get(userId);
                this.logger.info("rut " + userIndex++ + " de " + rut.length);
                for (int tagId : tags.keySet()) {
                    int tagIndex = tags.get(tagId);
                    this.logger.info("      rut tag " + tagIndex++ + " de " + tags.size());
                    double fixtu = fetchTagCount(tagId, userId, connection);
                    double fixt = countWithFixedTag(tagId, connection);
                    double fixu = countWithFixedUser(userId, connection);
                    rut[userIndex][tagIndex] = calculateRelationUT(
                            fixtu, fixu, allCountSum, fixt
                    );
                }
            }
            /*
             * create DA rank for each user.
             */
            for (int i = 0; i < ruuDA.length; i++) {
                aspirants.add(
                        new DvrecAspirant(
                                rank(i, projects.get(513), ruuDA, rup)
                        )
                );
            }
        } catch (Exception e) {
            this.logger.error("e", e);
        }
        return aspirants;
    }

    private double fetchTagCount(int tagId, int userId, Connection connection)
            throws SQLException {
        return new FetchTagCount(
                tagId, userId, connection
        ).count();
    }

    /**
     * Calculte the rut association
     *
     * @param fixtu       count of a tag for a user
     * @param fixu        count of all tags for a user
     * @param allCountSum count for all tags for all user
     * @param fixt        count for a tag for all users
     * @return rut association value
     */
    private double calculateRelationUT(
            double fixtu, double fixu, double allCountSum, double fixt
    ) {
        if (fixu == 0d || fixt == 0d) {
            return 0;
        }
        return (fixtu / fixu) * Math.log(allCountSum / fixt);
    }

    private final Map<Integer, Integer> mapFxu = new HashMap<>();

    private double countWithFixedUser(
            int userId, Connection connection
    ) throws SQLException {
        if (mapFxu.containsKey(userId)) {
            return mapFxu.get(userId);
        } else {
            int count = new FetchTagCount(
                    "gh_user_id=" + userId, "1", connection
            ).count();
            mapFxu.put(userId, count);
            return count;
        }
    }

    private final Map<Integer, Integer> mapFxt = new HashMap<>();

    private double countWithFixedTag(
            int tagId, Connection connection
    ) throws SQLException {
        if (mapFxt.containsKey(tagId)) {
            return mapFxt.get(tagId);
        } else {
            int count = new FetchTagCount(
                    "1", "ros_tag_id=" + tagId, connection
            ).count();
            mapFxt.put(tagId, count);
            return count;
        }
    }

    /**
     * Calculate rank.
     *
     * @param userIndex    user index on rup
     * @param projectIndex project index on rup
     * @param ruu          user-user relation matrix
     * @param rup          user-project relation matrix
     * @return rank
     */
    private double rank(
            int userIndex, int projectIndex, double[][] ruu, double[][] rup
    ) {
        double da = 0d;
        for (int i = 0; i < rup.length; i++) {
            if (rup[i][projectIndex] == 1) {
                da += ruu[userIndex][i];
            }
        }
        return da;
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
