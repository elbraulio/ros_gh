package iterator;

import dom.PagedDom;
import tools.StackWithInitialValues;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Iterates pages from {@link PagedDom} and retrieves a list of user links from
 * each page.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IterateUserPages implements Iterator<IterateUserLinks> {
    /**
     * The first value from the stack represent the current iterated page.
     * Just for keep the immutability of this class, by using this instead of
     * a mutable int that stores the current page on the iteration.
     */
    private final Stack<Integer> currentPage;

    /**
     * The paged dom that will be iterated.
     */
    private final PagedDom pagedDom;

    /**
     * Last page to iterate.
     */
    private final int lastPage;

    /**
     * Ctor.
     *
     * @param pagedDom    The paged dom that will be iterated.
     * @param initialPage Page where the iteration begins.
     * @param finalPage   Page where the iteration ends.
     */
    public IterateUserPages(
            final PagedDom pagedDom, final int initialPage, final int finalPage
    ) {
        this(
                new StackWithInitialValues<>(initialPage).stack(),
                pagedDom,
                finalPage
        );
    }

    /**
     * Ctor.
     * This Ctor. hides the {@link Stack} from the user.
     *
     * @param withInitialPage stack with the initial page.
     * @param pagedDom        The paged dom that will be iterated.
     * @param lastPage        Page where the iteration ends.
     */
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
