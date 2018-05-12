package user;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class InterestingTags implements FilterTag {
    @Override
    public Elements elements(final Document document) {
        return document.select("h2:contains(Interesting Tags)")
                .next()
                .select("li");
    }
}
