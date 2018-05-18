package tag;

import org.jsoup.nodes.Document;
import tag.FilterTag;
import tag.RosTag;
import tag.Tag;
import tools.CanBeList;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagList implements CanBeList<Tag> {
    private final Document document;
    private final FilterTag filterBy;

    public TagList(
            final Document document, final FilterTag filterBy
    ) {
        this.document = document;
        this.filterBy = filterBy;
    }

    @Override
    public List<Tag> asList() {
        final List<Tag> list = new LinkedList<>();
        this.filterBy.elements(this.document).forEach(
                element -> {
                    list.add(new RosTag(element.select("a").text()));
                }
        );
        return list;
    }

    @Override
    public List<Tag> take(int length) {
        return asList().subList(0, length);
    }
}
