package com.elbraulio.rosgh.tools;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface SqlQuery<T> {
    T query(Connection connection) throws SQLException;
}
