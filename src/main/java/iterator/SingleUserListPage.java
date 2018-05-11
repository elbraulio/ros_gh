package iterator;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tools.StackWithInitialValues;

import java.util.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class SingleUserListPage implements Iterator<String> {
    private final Elements elements;
    private final Stack<Integer> userDiv;

    public SingleUserListPage(final Document document) {
        this(
                document.select("div.user-info"),
                new StackWithInitialValues<>(0).stack()
        );
    }

    public SingleUserListPage(
            final Elements elements, final Stack<Integer> withInitialUserDiv
    ) {
        this.elements = elements;
        this.userDiv = withInitialUserDiv;
    }

    @Override
    public boolean hasNext() {
        return this.userDiv.peek() < this.elements.size();
    }

    @Override
    public String next() {
        final int currentDiv = this.userDiv.peek();
        if (currentDiv < this.elements.size()) {
            this.userDiv.push(currentDiv + 1);
            return this.elements.get(currentDiv)
                    .select("a[href]")
                    .attr("href");
        } else {
            throw new NoSuchElementException("index: " + currentDiv);
        }
    }
}
