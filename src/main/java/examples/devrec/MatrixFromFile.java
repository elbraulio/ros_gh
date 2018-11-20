package examples.devrec;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class MatrixFromFile implements Matrix {
    private final String path;
    private final int size;
    private final Logger logger = Logger.getLogger(MatrixFromFile.class);

    public MatrixFromFile(String path, int size) {
        this.path = path;
        this.size = size;
    }

    @Override
    public double[][] matrix() throws IOException {
        try (
                BufferedReader br = new BufferedReader(
                        new FileReader(this.path)
                )
        ) {
            double[][] ruu = new double[this.size][this.size];
            String line;
            String[] cols;
            for (int i = 0; i < this.size; i++) {
                line = br.readLine();
                cols = line.split(",");
                for (int j = 0; j < this.size; j++) {
                    ruu[i][j] = Double.parseDouble(cols[j]);
                }
            }
            return ruu;
        }
    }
}
