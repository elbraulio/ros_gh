package fetch;

import tools.IteratorAsList;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class UserLinks implements Iterator<String> {
    private final Iterator<SingleUserListPage> userListPages;
    private final List<String> links;
    private int nextIndex;

    public UserLinks(Iterator<SingleUserListPage> userListPages) {
        this.userListPages = userListPages;
        this.links = new LinkedList<>();
        nextIndex = 0;
    }

    @Override
    public boolean hasNext() {
        if (this.nextIndex < this.links.size()) {
            return true;
        } else {
            return this.userListPages.hasNext();
        }
    }

    @Override
    public String next() {
        if (this.nextIndex < this.links.size()) {
            return this.links.get(nextIndex++);
        } else {
            if (this.userListPages.hasNext()) {
                this.links.addAll(
                        new IteratorAsList<>(
                                this.userListPages.next()
                        ).asList()
                );
                return next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
