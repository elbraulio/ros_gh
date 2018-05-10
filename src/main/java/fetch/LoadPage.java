package fetch;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface LoadPage {
    Document page(int index) throws IOException;
}
