package com.elbraulio.rosgh.algorithm;

import com.elbraulio.rosgh.tag.Tag;
import com.elbraulio.rosgh.tools.HtmlContent;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface Question {
    String title();

    int author();

    String date();

    List<Tag> tags();

    List<String> collaborators();

    HtmlContent content();

    String url();

    int viewCount();

    int votes();
}
