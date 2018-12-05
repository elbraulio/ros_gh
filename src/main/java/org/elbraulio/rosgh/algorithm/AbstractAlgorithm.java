package org.elbraulio.rosgh.algorithm;

import examples.devrec.DefaultTaggedItem;

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

    protected abstract List<Aspirant> feed(DefaultTaggedItem item);

    @Override
    public List<Aspirant> aspirants(DefaultTaggedItem item) {
        return sort.orderedList(feed(item));
    }
}
