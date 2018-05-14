package tag;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tag.FilterTag;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class NormalTags implements FilterTag {
    @Override
    public Elements elements(final Document document) {
        return document.select("#ab-user-tags > li");
    }
}
