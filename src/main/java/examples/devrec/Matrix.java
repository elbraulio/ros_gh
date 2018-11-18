package examples.devrec;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Matrix {
    double[][] matrix() throws SQLException, IOException;
}
