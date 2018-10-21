package org.elbraulio.rosgh.iterator;

import org.jsoup.nodes.Document;

import java.util.Iterator;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface BuildIteratorForContent {
    Iterator<String> iterator(Document document);
}
