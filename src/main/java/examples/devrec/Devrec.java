package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.Question;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class Devrec implements Algorithm {

    private final Logger logger = Logger.getLogger(Devrec.class);
    private final double[][] ruuDA;
    private final double[][] ruuKA;
    private final Connection connection;
    private final Map<Integer, Integer> users;

    public Devrec(Connection connection) throws SQLException {
        this(
                connection,
                new FetchIndexedProjects(
                        connection
                ).map(),
                new FetchIndexedUsers(
                        connection
                ).map(),
                new FetchTags(connection).map()
        );
    }

    public Devrec(
            Connection connection, Map<Integer, Integer> projects,
            Map<Integer, Integer> users, Map<Integer, Integer> tags
    ) throws SQLException {
        this(
                connection,
                projects, users, tags,
                new Rup(connection, projects, users).matrix(),
                new Rut(
                        connection, tags, users,
                        new FetchTagCount(connection).count()
                ).matrix()
        );
    }

    public Devrec(
            Connection connection, Map<Integer, Integer> projects,
            Map<Integer, Integer> users, Map<Integer, Integer> tags,
            double[][] rup, double[][] rut
    ) {
        this(
                connection, users,
                new RuuDA(users, rup).matrix(),
                new RuuKA(users, rut).matrix()
        );
    }

    public Devrec(
            Connection connection,
            Map<Integer, Integer> users,
            double[][] ruuDA, double[][] ruuKA
    ) {
        this.connection = connection;
        this.users = users;
        this.ruuDA = ruuDA;
        this.ruuKA = ruuKA;
    }

    @Override
    public List<Aspirant> aspirants(Question question) throws SQLException {
        List<Aspirant> aspirants = new LinkedList<>();
        for (int i = 0; i < ruuDA.length; i++) {
            this.logger.info("rank for user " + i + " of " + ruuDA.length);
            aspirants.add(
                    new DvrecAspirant(
                            findId(i, users, -1),
                            rank(
                                    i, 513, ruuDA, users,
                                    new CheckProject(connection)
                            ),
                            rank(
                                    i, 8, ruuKA, users,
                                    new CheckTag(connection)
                            )
                    )
            );
        }
        return aspirants;
    }


    /**
     * Calculate rank.
     *
     * @param userIndex user index on ruu
     * @param relatedId related tag or project id on db
     * @param ruu       user-user relation matrix
     * @return rank
     */
    private double rank(
            int userIndex, int relatedId, double[][] ruu,
            Map<Integer, Integer> users, CheckRelated checkRelated
    ) throws SQLException {
        double rank = 0d;
        List<Integer> relatedUsers = checkRelated.isRelated(relatedId);
        for (int userId : relatedUsers) {
            this.logger.info(
                    "  relation to user " + userId + " of " + relatedUsers.size()
            );
            if (this.users.containsKey(userId)) {
                rank += ruu[userIndex][users.get(userId)];
            }
        }
        return rank;
    }

    private int findId(int value, Map<Integer, Integer> idIndex, int onDefault) {
        for (Map.Entry<Integer, Integer> entry : idIndex.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return onDefault;
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

        private final int id;
        private final double da;
        private final double ka;

        public DvrecAspirant(int id, double da, double ka) {
            this.id = id;
            this.da = da;
            this.ka = ka;
        }

        @Override
        public double rank() {
            return this.ka;
        }

        @Override
        public String toString() {
            return "{id: " + this.id + ", rank: " + this.rank() + "}\n";
        }
    }
}
