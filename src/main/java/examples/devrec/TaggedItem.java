package examples.devrec;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface TaggedItem {

    int projectId();

    List<Integer> tags();
}
