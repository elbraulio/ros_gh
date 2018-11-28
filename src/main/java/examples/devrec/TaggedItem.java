package examples.devrec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class TaggedItem {
    public int userId() {
        return 513;
    }

    public List<Integer> tags() {
        final List<Integer> list = new ArrayList<>(1);
        list.add(49);
        return list;
    }
}
