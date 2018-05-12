package iterator;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import tools.StackWithInitialValues;

import java.util.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IterateUserLinks implements Iterator<String> {
    private final Elements elements;
    private final Stack<Integer> nextDivIndex;

    public IterateUserLinks(final Document document) {
        this(
                document.select("div.user-info"),
                new StackWithInitialValues<>(0).stack()
        );
    }

    private IterateUserLinks(
            final Elements elements, final Stack<Integer> withInitialIndexDiv
    ) {
        this.elements = elements;
        this.nextDivIndex = withInitialIndexDiv;
    }

    @Override
    public boolean hasNext() {
        return this.nextDivIndex.peek() < this.elements.size();
    }

    @Override
    public String next() {
        final int currentDiv = this.nextDivIndex.peek();
        if (currentDiv < this.elements.size()) {
            this.nextDivIndex.push(currentDiv + 1);
            return this.elements.get(currentDiv)
                    .select("a[href]")
                    .attr("href");
        } else {
            throw new NoSuchElementException("index: " + currentDiv);
        }
    }
}
