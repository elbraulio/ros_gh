package fetch;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface PagedDom {
    Document page(final int index) throws IOException;
}
