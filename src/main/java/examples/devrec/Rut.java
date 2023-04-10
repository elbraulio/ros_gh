package examples.devrec;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class Rut implements Matrix {
    private final Connection connection;
    private final Map<Integer, Integer> tags;
    private final Map<Integer, Integer> users;
    private final int allTagCount;
    private final Logger logger = LogManager.getLogger(Rut.class);

    public Rut(
            Connection connection, Map<Integer, Integer> tags,
            Map<Integer, Integer> users,
            int allTagCount) {

        this.connection = connection;
        this.tags = tags;
        this.users = users;
        this.allTagCount = allTagCount;
    }

    @Override
    public double[][] matrix() throws SQLException {
        double[][] rut = new double[users.size()][tags.size()];
        for (int userId : users.keySet()) {
            int userIndex = users.get(userId);
            this.logger.info("rut " + userIndex + " de " + rut.length);
            List<Integer> realtedTags =
                    new RelatedTags(this.connection).isRelated(userId);
            for (int tagId : realtedTags) {
                int tagIndex = tags.get(tagId);
                this.logger.info("      rut tag " + tagIndex + " de " + tags.size());
                double fixtu = new FetchTagCount(
                        tagId, userId, connection
                ).count();
                double fixt = countWithFixedTag(tagId, connection);
                double fixu = countWithFixedUser(userId, connection);
                rut[userIndex][tagIndex] = calculateRelationUT(
                        fixtu, fixu, allTagCount, fixt
                );
            }
        }
        return rut;
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
}
