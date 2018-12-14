package com.elbraulio.rosgh.tag;

import com.elbraulio.rosgh.tools.SqlQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosTagExist implements SqlQuery<Boolean> {
    private final String tag;

    public RosTagExist(String tag) {
        this.tag = tag;
    }

    @Override
    public Boolean query(Connection connection) throws SQLException {
        String userExists = "SELECT * FROM ros_tag " +
                "WHERE name='" + this.tag + "'";
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(userExists)
        ) {
            return rs.next();
        }
    }
}
