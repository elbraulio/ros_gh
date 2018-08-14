package question;

import tag.Tag;
import tools.HtmlContent;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface RosQuestion {
    String title();

    String author();

    String date();

    List<Tag> tags();

    List<String> participants();

    HtmlContent body();

    String link();
}
