package com.elbraulio.rosgh.iterator;

import com.elbraulio.rosgh.tools.IteratorAsList;
import com.elbraulio.rosgh.tools.StackWithInitialValues;

import java.util.*;

/**
 * Iterates a {@link IterateDomPages}. Retrieving each user url from each
 * page.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class IteratePagedContent <T> implements Iterator<T> {
    /**
     * Users list page iterator.
     */
    private final Iterator<Iterator<T>> iterator;

    /**
     * Each url retrieved from the iterator.
     */
    private final List<T> links;

    /**
     * The first value from the stack represent the current iterated url index.
     * Just for keep the immutability of this class, by using this instead of
     * a mutable int that stores the current index on the iteration.
     */
    private final Stack<Integer> nextLinkIndex;

    /**
     * Ctor.
     *
     * @param iterator users list page iterator.
     */
    public IteratePagedContent(final Iterator<Iterator<T>> iterator) {
        this(
                iterator,
                new LinkedList<>(),
                new StackWithInitialValues<>(0).stack()
        );
    }

    /**
     * Ctor.
     * This Ctor. hides the {@link Stack} from the user.
     *
     * @param iterator users list page iterator.
     * @param links         initial set of links retrieved from the iterator.
     * @param nextLinkIndex current iterated url index.
     */
    private IteratePagedContent(
            final Iterator<Iterator<T>> iterator,
            final List<T> links,
            final Stack<Integer> nextLinkIndex
    ) {

        this.iterator = iterator;
        this.links = links;
        this.nextLinkIndex = nextLinkIndex;
    }

    @Override
    public boolean hasNext() {
        if (this.nextLinkIndex.peek() < this.links.size()) {
            return true;
        } else {
            return this.iterator.hasNext();
        }
    }

    @Override
    public T next() {
        final int currentIndex = this.nextLinkIndex.peek();
        if (currentIndex < this.links.size()) {
            this.nextLinkIndex.push(currentIndex + 1);
            return this.links.get(currentIndex);
        } else {
            if (this.iterator.hasNext()) {
                this.links.addAll(
                        new IteratorAsList<>(
                                this.iterator.next()
                        ).asList()
                );
                return next();
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}
