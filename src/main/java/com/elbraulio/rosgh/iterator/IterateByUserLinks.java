package com.elbraulio.rosgh.iterator;

import org.jsoup.nodes.Document;

import java.util.Iterator;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateByUserLinks implements BuildIteratorForContent {
    @Override
    public Iterator<String> iterator(Document document) {
        return new IterateUserLinks(document);
    }
}
