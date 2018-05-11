package fetch;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class UserListPages implements Iterator<SingleUserListPage> {
    private int currentPage;
    private final LoadPage loadPage;
    private int totalPages;

    public UserListPages(LoadPage loadPage, int initialPage, int finalPage) {
        this.loadPage = loadPage;
        this.totalPages = finalPage;
        this.currentPage = initialPage;
    }

    @Override
    public boolean hasNext() {
        return currentPage <= totalPages;
    }

    @Override
    public SingleUserListPage next() {
        try {
            return new SingleUserListPage(this.loadPage.page(currentPage++));
        } catch (IOException e) {
            throw new NoSuchElementException(
                    "page: " + this.currentPage + " | " + e.getMessage()
            );
        }
    }
}
