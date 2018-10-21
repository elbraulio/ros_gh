package org.elbraulio.rosgh.tools;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class LastRosAnswersPage {
    private final Document document;

    public LastRosAnswersPage(Document document) {
        this.document = document;
    }

    public int value(){
        final Elements elements = this.document.select(
                ".paginator > .next"
        );
        return Integer.parseInt(
                elements.get(0)
                        .previousElementSibling()
                        .child(0).text()
        );
    }
}
