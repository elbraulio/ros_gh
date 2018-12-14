package com.elbraulio.rosgh.question;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface ApiRosQuestion {
    List<String> tags();

    int answerCount();

    int acceptedAnswerId();

    List<Integer> answerIds();

    int id();

    int lastActivityBy();

    int viewCount();

    String lastActivityAt();

    String title();

    String url();

    int author();

    String addedAt();

    String summary();
}
