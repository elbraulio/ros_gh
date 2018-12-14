package org.elbraulio.rosgh.tag;

import org.elbraulio.rosgh.tools.CanBeList;
import org.elbraulio.rosgh.tools.ParseRosTagCount;
import org.jsoup.nodes.Document;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class TagWithCountList implements CanBeList<TagWithCount> {
    private final Document document;
    private final FilterTag filterBy;

    public TagWithCountList(final Document document, final FilterTag filterBy) {
        this.document = document;
        this.filterBy = filterBy;
    }

    @Override
    public List<TagWithCount> asList() {
        final List<TagWithCount> list = new LinkedList<>();
        this.filterBy.elements(this.document).forEach(
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

    @Override
    public List<TagWithCount> take(int length) {
        return asList().subList(0, length);
    }
}
