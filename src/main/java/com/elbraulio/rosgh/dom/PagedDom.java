package com.elbraulio.rosgh.dom;

import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Represent a paged web interface, wiches allow you to ask for a certain
 * page using {@link PagedDom#page(int)}.
 *
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface PagedDom {
    /**
     * Return {@link Document} representing the requested page's DOM.
     *
     * @param index requested page number.
     * @return Dom from the requested page number.
     * @throws IOException when page does not exist.
     */
    Document page(final int index) throws IOException;
}
