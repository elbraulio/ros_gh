package com.elbraulio.rosgh.iterator;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateQuestionsLinks implements Iterator<String> {

    private int elementIndex;
    private final Elements elements;

    // TODO: 18-06-18 forget about stacks and make object that hides that
    public IterateQuestionsLinks(final Document document) {
        this.elements = document.select("div.short-summary");
        this.elementIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return this.elementIndex < this.elements.size();
    }

    @Override
    public String next() {
        if (this.elementIndex < this.elements.size()) {
            return this.elements.get(this.elementIndex++)
                    .select("h2 > a[href]")
                    .attr("href");
        } else {
            throw new NoSuchElementException("index: " + this.elementIndex);
        }
    }
}
