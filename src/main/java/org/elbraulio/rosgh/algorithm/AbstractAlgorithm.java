package org.elbraulio.rosgh.algorithm;

import examples.devrec.TaggedItem;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public abstract class AbstractAlgorithm implements Algorithm {

    private final Sort sort;

    public AbstractAlgorithm() {
        this(new ByRankDesc());
    }

    public AbstractAlgorithm(Sort sort) {

        this.sort = sort;
    }

    protected abstract List<Aspirant> feed(TaggedItem item);

    @Override
    public List<Aspirant> aspirants(TaggedItem item) {
        return sort.orderedList(feed(item));
    }
}
