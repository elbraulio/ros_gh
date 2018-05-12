package iterator;

import dom.PagedDom;
import tools.StackWithInitialValues;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IterateUserPages implements Iterator<IterateUserLinks> {
    private final Stack<Integer> currentPage;
    private final PagedDom pagedDom;
    private final int lastPage;

    public IterateUserPages(
            final PagedDom pagedDom, final int initialPage, final int finalPage
    ) {
        this(
                new StackWithInitialValues<>(initialPage).stack(),
                pagedDom,
                finalPage
        );
    }

    private IterateUserPages(
            final Stack<Integer> withInitialPage,
            final PagedDom pagedDom,
            final int lastPage
    ) {
        this.currentPage = withInitialPage;
        this.pagedDom = pagedDom;
        this.lastPage = lastPage;
    }

    @Override
    public boolean hasNext() {
        return this.currentPage.peek() <= this.lastPage;
    }

    @Override
    public IterateUserLinks next() {
        try {
            final int page = this.currentPage.peek();
            this.currentPage.push(page + 1);
            return new IterateUserLinks(this.pagedDom.page(page));
        } catch (IOException e) {
            throw new NoSuchElementException(
                    "page: " + this.currentPage.peek() + " | " + e.getMessage()
            );
        }
    }
}
