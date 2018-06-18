package dom;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosAnswersPagedDom implements PagedDom {

    final String webUrl =
            "https://answers.ros.org/questions/scope:all/sort:age-desc/page:";

    @Override
    public Document page(int index) throws IOException {
        return Jsoup.connect(this.webUrl + index).get();
    }
}
