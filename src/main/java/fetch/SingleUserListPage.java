package fetch;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SingleUserListPage implements Iterator<String> {
    private final Elements elements;
    private int currentUserDiv;

    public SingleUserListPage(Document document) {

        this.elements = document.select("div.user-info");
        this.currentUserDiv = 0;
    }

    @Override
    public boolean hasNext() {
        return this.currentUserDiv < this.elements.size();
    }

    @Override
    public String next() {
        if (this.currentUserDiv < this.elements.size()) {
            return this.elements.get(currentUserDiv++)
                    .select("a[href]")
                    .attr("href");
        } else {
            throw new NoSuchElementException("index: " + this.currentUserDiv);
        }
    }
}
