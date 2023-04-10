package examples.devrec;



import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * create Ruu association matrix filled with 0's. This matrix is symmetric.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RuuDA implements Matrix {
    private final Map<Integer, Integer> users;
    private final double[][] rup;
    private final Logger logger = LogManager.getLogger(RuuDA.class);

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
                ruuDA[j][i] = ruuDA[i][j];
            }
        }
        try {
            new SaveRuuToFile(ruuDA, "ruuDA").save();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ruuDA;
    }
}
