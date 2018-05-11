package fetch;

import tools.StackWithInitialValues;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class UserListPages implements Iterator<SingleUserListPage> {
    private final Stack<Integer> currentPage;
    private final LoadPage loadPage;
    private final int totalPages;

    public UserListPages(
            final LoadPage loadPage, final int initialPage, final int finalPage
    ) {
        this(
                new StackWithInitialValues<>(initialPage).stack(),
                loadPage,
                finalPage
        );
    }

    public UserListPages(
            final Stack<Integer> withInitialPage,
            final LoadPage loadPage,
            final int totalPages
    ) {
        this.currentPage = withInitialPage;
        this.loadPage = loadPage;
        this.totalPages = totalPages;
    }

    @Override
    public boolean hasNext() {
        return this.currentPage.peek() <= this.totalPages;
    }

    @Override
    public SingleUserListPage next() {
        try {
            final int page = this.currentPage.peek();
            this.currentPage.push(page + 1);
            return new SingleUserListPage(this.loadPage.page(page));
        } catch (IOException e) {
            throw new NoSuchElementException(
                    "page: " + this.currentPage.peek() + " | " + e.getMessage()
            );
        }
    }
}
