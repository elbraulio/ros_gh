package fetch;

import tools.IteratorAsList;
import tools.StackWithInitialValues;

import java.util.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class UserLinks implements Iterator<String> {
    private final Iterator<SingleUserListPage> userListPages;
    private final List<String> links;
    private final Stack<Integer> nextIndex;

    public UserLinks(final Iterator<SingleUserListPage> userListPages) {
        this(
                userListPages,
                new LinkedList<>(),
                new StackWithInitialValues<>(0).stack()
        );
    }

    public UserLinks(
            final Iterator<SingleUserListPage> userListPages,
            final List<String> links,
            final Stack<Integer> nextIndex
    ){

        this.userListPages = userListPages;
        this.links = links;
        this.nextIndex = nextIndex;
    }

    @Override
    public boolean hasNext() {
        if (this.nextIndex.peek() < this.links.size()) {
            return true;
        } else {
            return this.userListPages.hasNext();
        }
    }

    @Override
    public String next() {
        final int currentIndex = this.nextIndex.peek();
        if (currentIndex < this.links.size()) {
            this.nextIndex.push(currentIndex + 1);
            return this.links.get(currentIndex);
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
