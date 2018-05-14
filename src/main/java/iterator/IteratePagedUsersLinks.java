package iterator;

import tools.IteratorAsList;
import tools.StackWithInitialValues;

import java.util.*;

/**
 * Iterates a {@link IterateUserPages}. Retrieving each user link from each
 * page.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IteratePagedUsersLinks implements Iterator<String> {
    /**
     * Users list page iterator.
     */
    private final Iterator<IterateUserLinks> pagedUserList;

    /**
     * Each link retrieved from the iterator.
     */
    private final List<String> links;

    /**
     * The first value from the stack represent the current iterated link index.
     * Just for keep the immutability of this class, by using this instead of
     * a mutable int that stores the current index on the iteration.
     */
    private final Stack<Integer> nextLinkIndex;

    /**
     * Ctor.
     *
     * @param pagedUserList users list page iterator.
     */
    public IteratePagedUsersLinks(final Iterator<IterateUserLinks> pagedUserList) {
        this(
                pagedUserList,
                new LinkedList<>(),
                new StackWithInitialValues<>(0).stack()
        );
    }

    /**
     * Ctor.
     * This Ctor. hides the {@link Stack} from the user.
     *
     * @param pagedUserList users list page iterator.
     * @param links         initial set of links retrieved from the iterator.
     * @param nextLinkIndex current iterated link index.
     */
    private IteratePagedUsersLinks(
            final Iterator<IterateUserLinks> pagedUserList,
            final List<String> links,
            final Stack<Integer> nextLinkIndex
    ) {

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
