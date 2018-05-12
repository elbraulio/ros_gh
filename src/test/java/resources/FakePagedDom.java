package resources;

import fetch.PagedDom;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FakePagedDom implements PagedDom {
    @Override
    public Document page(final int index) throws IOException {
        return Jsoup.parse(
                new File(
                        "src/test/java/resources/usersPage"
                                + index + ".txt"
                ), "UTF-8"
        );
    }
}
