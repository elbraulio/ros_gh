package examples.devrec;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SaveRuuToFile {
    private final double[][] ruu;
    private final String path;

    public SaveRuuToFile(double[][] ruu, String path) {
        this.ruu = ruu;
        this.path = path;
    }

    public void save() throws IOException {
        try (
                final BufferedWriter bw = new BufferedWriter(
                        new FileWriter(this.path)
                )
        ) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.ruu.length; i++) {
                sb.append(ruu[i][0]);
                for (int j = 1; j < ruu[i].length; j++) {
                    sb.append(",");
                    sb.append(ruu[i][j]);
                }
                sb.append("\n");
            }
            bw.write(sb.toString());
        }
    }
}
