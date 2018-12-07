package examples.devrec;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * create Ruu association matrix filled with 0's. This matrix is symmetric.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RuuDA implements Matrix {
    private final Map<Integer, Integer> users;
    private final double[][] rup;
    private final Logger logger = Logger.getLogger(RuuDA.class);

    public RuuDA(Map<Integer, Integer> users, double[][] rup) {

        this.users = users;
        this.rup = rup;
    }

    @Override
    public double[][] matrix() {
        double[][] ruuDA = new double[users.size()][users.size()];
        for (int i = 0; i < ruuDA.length; i++) {
            this.logger.info("ruuDA " + i + " de " + ruuDA.length);
            for (int j = i; j < ruuDA.length; j++) {
                ruuDA[i][j] = new JaccardSimilarity(i, j, rup).val();
                ruuDA[j][i] = new JaccardSimilarity(i, j, rup).val();
            }
        }
        return ruuDA;
    }
}
