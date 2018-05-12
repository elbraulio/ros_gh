package iterator;

import tools.IteratorAsList;
import tools.StackWithInitialValues;

import java.util.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IteratePagedUsersLinks implements Iterator<String> {
    private final Iterator<IterateUserLinks> pagedUserList;
    private final List<String> links;
    private final Stack<Integer> nextLinkIndex;

    public IteratePagedUsersLinks(final Iterator<IterateUserLinks> pagedUserList) {
        this(
                pagedUserList,
                new LinkedList<>(),
                new StackWithInitialValues<>(0).stack()
        );
    }

    private IteratePagedUsersLinks(
            final Iterator<IterateUserLinks> pagedUserList,
            final List<String> links,
            final Stack<Integer> nextLinkIndex
    ){

        this.pagedUserList = pagedUserList;
        this.links = links;
        this.nextLinkIndex = nextLinkIndex;
    }

    @Override
    public boolean hasNext() {
        if (this.nextLinkIndex.peek() < this.links.size()) {
            return true;
        } else {
            return this.pagedUserList.hasNext();
        }
    }

    @Override
    public String next() {
        final int currentIndex = this.nextLinkIndex.peek();
        if (currentIndex < this.links.size()) {
            this.nextLinkIndex.push(currentIndex + 1);
            return this.links.get(currentIndex);
        } else {
            if (this.pagedUserList.hasNext()) {
                this.links.addAll(
                        new IteratorAsList<>(
                                this.pagedUserList.next()
                        ).asList()
                );
                return next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
