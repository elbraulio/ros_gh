package org.elbraulio.rosgh.tools;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface CanBeList<T> {
    List<T> asList();

    List<T> take(final int length);
}
