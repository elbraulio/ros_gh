package examples.devrec;

import com.elbraulio.rosgh.algorithm.Algorithm;
import com.elbraulio.rosgh.algorithm.Aspirant;
import com.elbraulio.rosgh.algorithm.TaggedItem;
import com.elbraulio.rosgh.tag.Tag;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class Devrec implements Algorithm {

    private final Logger logger = LogManager.getLogger(Devrec.class);
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

    /**
     * All aspirants evaluated with all tags and only with their best rank.
     *
     * @param item taggedItem
     * @return Aspirants to solve a taggedItem
     */
    @Override
    public List<Aspirant> aspirants(TaggedItem item) {
        Map<Integer, Aspirant> aspirants = new HashMap<>();
        try {
            int count = 1;
            for (Integer repo : item.repos()) {
                this.logger.info("rank for user " + count++ + " of " + item.repos().size());
                for (Tag tag : item.tags()) {
                    for (int i = 0; i < ruuDA.length; i++) {
                        Aspirant newAspirant = new DevrecAspirant(
                                findId(i, users, -1),
                                rank(
                                        i, repo, ruuDA, users,
                                        new CheckProject(connection)
                                ),

                                rank(
                                        i, tag.id(), ruuKA, users,
                                        new CheckTag(connection)
                                )
                        );
                        if (aspirants.containsKey(newAspirant.id())) {
                            if (aspirants.get(newAspirant.id()).rank() < newAspirant.rank()) {
                                aspirants.put(newAspirant.id(), newAspirant);
                            }
                        } else {
                            aspirants.put(newAspirant.id(), newAspirant);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            this.logger.error("sql error", e);
        }
        return new ArrayList<>(aspirants.values());
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
            if (this.users.containsKey(userId)) {
                rank += ruu[userIndex][users.get(userId)];
            }
        }
        return Double.isNaN(rank) ? 0d : rank;
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

    final class DevrecAspirant implements Aspirant {

        private final int id;
        private final double da;
        private final double ka;

        public DevrecAspirant(int id, double da, double ka) {
            this.id = id;
            this.da = da;
            this.ka = ka;
        }

        @Override
        public double rank() {
            return this.ka * 0.75 + this.da * 0.25;
        }

        @Override
        public int id() {
            return this.id;
        }

        @Override
        public String toString() {
            return "DevrecAspirant{" +
                    "id=" + id +
                    ", da=" + da +
                    ", ka=" + ka +
                    ", rank= " + rank() +
                    '}' + '\n';
        }
    }
}
