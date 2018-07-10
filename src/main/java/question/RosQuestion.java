package question;

import tag.Tag;
import tools.HtmlContent;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface RosQuestion {
    String title();

    int author();

    String date();

    List<Tag> tags();

    List<String> participants();

    HtmlContent body();

    String url();

    int rosId();

    int answerCount();

    int acceptedAnswerId();

    long lastActivityAt();

    int lastActivityBy();

    int viewCount();

    int votes();
}
