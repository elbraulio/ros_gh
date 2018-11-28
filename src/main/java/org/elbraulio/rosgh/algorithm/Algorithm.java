package org.elbraulio.rosgh.algorithm;

import examples.devrec.TagedItem;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Algorithm {
    List<Aspirant> aspirants(TagedItem item) throws SQLException;
}
