package tag;

import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class QuestionTags {
    private final Document document;

    public QuestionTags(Document document) {
        this.document = document;
    }

    public List<Tag> asList() {
        final List<Tag> tags = new LinkedList<>();
        this.document.select("ul.post-tags > li > div > a")
                .forEach(
                        e -> tags.add(new RosTag(e.text()))
                );
        return tags;
    }
}
