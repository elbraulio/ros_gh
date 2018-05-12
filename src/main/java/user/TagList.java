package user;

import org.jsoup.nodes.Document;
import tools.ParseRosTagCount;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagList {
    private final Document document;
    private final FilterTag filterBy;

    public TagList(
            final Document document, final FilterTag filterBy
    ) {
        this.document = document;
        this.filterBy = filterBy;
    }

    public List<Tag> list() {
        final List<Tag> list = new LinkedList<>();
        this.filterBy.elements(document).forEach(
                element -> {
                    list.add(new RosTag(element.select("a").text()));
                }
        );
        return list;
    }
}
