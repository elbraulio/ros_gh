package tag;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tag.FilterTag;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IgnoredTags implements FilterTag {
    @Override
    public Elements elements(final Document document) {
        return document.select("h2:contains(Ignored Tag)")
                .next()
                .select("li");
    }
}
