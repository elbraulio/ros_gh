package fetch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosLoadPage implements LoadPage {
    private final String webUrl =
            "https://answers.ros.org/users/?sort=reputation&page=";

    @Override
    public Document page(final int index) throws IOException {
        return Jsoup.connect(this.webUrl + index).get();
    }
}
