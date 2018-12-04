package com.elbraulio.rosgh.algorithm;

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

    protected abstract List<Aspirant> feed(Question question);

    @Override
    public List<Aspirant> aspirants(Question question) {
        return sort.orderedList(feed(question));
    }
}
