package com.elbraulio.rosgh.health;

import com.elbraulio.rosgh.algorithm.Aspirant;
import com.elbraulio.rosgh.algorithm.TaggedItem;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Accuracy {
    double precision(List<Aspirant> aspirants, TaggedItem question);

    double recall(List<Aspirant> aspirants, TaggedItem question);

    double mrr(List<Aspirant> aspirants, TaggedItem question);
}
