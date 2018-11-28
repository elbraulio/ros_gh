package org.elbraulio.rosgh.algorithm;

import examples.devrec.TaggedItem;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Algorithm {
    List<Aspirant> aspirants(TaggedItem item) throws SQLException;
}
