package com.elbraulio.rosgh.algorithm;

import com.elbraulio.rosgh.question.Answer;
import com.elbraulio.rosgh.tag.Tag;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface TaggedItem {

    int questionId();

    String title();

    int author();

    long addedAt();

    String summary();

    String url();

    int lastActivityBy();

    int viewCount();

    int votes();

    List<Tag> tags();

    List<Answer> answers();

    List<Integer> repos();
}
