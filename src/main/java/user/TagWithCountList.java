package user;

import org.jsoup.nodes.Document;
import tools.ParseRosTagCount;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagWithCountList {
    private final Document document;
    private final FilterTag filterBy;

    public TagWithCountList(final Document document, final FilterTag filterBy) {
        this.document = document;
        this.filterBy = filterBy;
    }

    public List<TagWithCount> list() {
        final List<TagWithCount> list = new LinkedList<>();
        this.filterBy.elements(document).forEach(
                element -> {
                    final String name = element.select("a").text();
                    final int count = new ParseRosTagCount(
                            element.select(".tag-number").text()
                            ).count();
                    list.add(new WithCount(name, count));
                }
        );
        return list;
    }
}
