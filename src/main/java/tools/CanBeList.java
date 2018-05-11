package tools;

import java.util.List;
import java.util.zip.ZipFile;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface CanBeList<T> {
    List<T> asList();

    List<T> take(final int length);
}
