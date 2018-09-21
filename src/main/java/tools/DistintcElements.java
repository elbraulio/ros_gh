package tools;

import java.util.HashSet;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DistintcElements<T> {
    private final List<T> list;

    public DistintcElements(List<T> list) {
        this.list = list;
    }

    public List<T> list() {
        return new IteratorAsList<>(
                new HashSet<>(this.list)
                        .iterator()
        ).asList();
    }
}
