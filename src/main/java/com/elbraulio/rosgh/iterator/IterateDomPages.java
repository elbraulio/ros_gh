package com.elbraulio.rosgh.iterator;

import com.elbraulio.rosgh.dom.PagedDom;
import com.elbraulio.rosgh.tools.StackWithInitialValues;

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
public final class IterateDomPages implements Iterator<Iterator<String>> {
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
    private final BuildIteratorForContent buildIteratorForContent;

    /**
     * @param pagedDom                The paged dom that will be iterated.
     * @param initialPage             Page where the iteration begins.
     * @param finalPage               Page where the iteration ends.
     * @param buildIteratorForContent content iterator.
     */
    public IterateDomPages(
            final PagedDom pagedDom, final int initialPage, final int finalPage,
            final BuildIteratorForContent buildIteratorForContent
    ) {
        this(
                new StackWithInitialValues<>(initialPage).stack(),
                pagedDom,
                finalPage,
                buildIteratorForContent
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
    private IterateDomPages(
            final Stack<Integer> withInitialPage,
            final PagedDom pagedDom,
            final int lastPage,
            final BuildIteratorForContent buildIteratorForContent
    ) {
        this.currentPage = withInitialPage;
        this.pagedDom = pagedDom;
        this.lastPage = lastPage;
        this.buildIteratorForContent = buildIteratorForContent;
    }

    @Override
    public boolean hasNext() {
        return this.currentPage.peek() <= this.lastPage;
    }

    @Override
    public Iterator<String> next() {
        try {
            final int page = this.currentPage.peek();
            this.currentPage.push(page + 1);
            return buildIteratorForContent.iterator(this.pagedDom.page(page));
        } catch (IOException e) {
            throw new NoSuchElementException(
                    "page: " + this.currentPage.peek() + " | " + e.getMessage()
            );
        }
    }
}
