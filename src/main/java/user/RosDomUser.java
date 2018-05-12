package user;

import org.jsoup.nodes.Document;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosDomUser {
    private final Document document;

    public RosDomUser(final Document document) {
        this.document = document;
    }

    public String name() {
        return this.document.select(".section-title")
                .get(0).text().split("'")[0];
    }
}
