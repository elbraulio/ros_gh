package examples.devrec;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface CheckRelated {
    List<Integer> isRelated(int relatedId) throws SQLException;
}
