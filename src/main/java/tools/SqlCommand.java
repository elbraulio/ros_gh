package tools;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SqlCommand {
    int execute(Connection connection, int defaultValue) throws SQLException, IOException, InterruptedException;
}
