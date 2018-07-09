package tools;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SqliteConnection {
    private final String path;

    public SqliteConnection(final String path) {
        this.path = path;
    }

    public Connection connection() throws SQLException, ClassNotFoundException {
        final String url = "jdbc:sqlite:" + this.path;
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(url);
    }
}
