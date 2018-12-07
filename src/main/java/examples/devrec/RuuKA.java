package examples.devrec;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

/**
 * create Ruu association matrix filled with 0's. This matrix is symmetric.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RuuKA implements Matrix {

    private final Logger logger = Logger.getLogger(Devrec.class);
    private final Map<Integer, Integer> users;
    private final double[][] rut;

    public RuuKA(Map<Integer, Integer> users, double[][] rut) {

        this.users = users;
        this.rut = rut;
    }

    @Override
    public double[][] matrix() {
        double[][] ruuKA = new double[users.size()][users.size()];
        for (int i = 0; i < ruuKA.length; i++) {
            this.logger.info("ruuKA " + i + " de " + ruuKA.length);
            for (int j = i; j < ruuKA.length; j++) {
                ruuKA[i][j] = vectorSpace(i, j, rut);
                ruuKA[j][i] = ruuKA[i][j];
            }
        }
        return ruuKA;
    }

    private double vectorSpace(int i, int j, double[][] rut) {
        double sum = 0d;
        for (int k = 0; k < rut[i].length; k++) {
            sum += rut[i][k] * rut[j][k];
        }
        return sum;
    }
}
