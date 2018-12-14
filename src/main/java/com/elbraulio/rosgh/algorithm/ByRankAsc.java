package com.elbraulio.rosgh.algorithm;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ByRankAsc implements Sort {
    @Override
    public List<Aspirant> orderedList(List<Aspirant> feed) {
        final List<Aspirant> ordered =  new LinkedList<>(feed);
        ordered.sort(
                Comparator.comparingDouble(
                        value -> value.rank()
                )
        );
        return ordered;
    }
}
