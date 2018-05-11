package fetch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FakeLoadPage implements LoadPage {
    @Override
    public Document page(int index) throws IOException {
        return Jsoup.parse(
                new File(
                        "src/test/java/resources/users"
                                + index + ".txt"
                ), "UTF-8"
        );
    }
}
