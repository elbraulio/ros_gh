package user;

import java.util.Collection;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface RosUser {
    String name();

    int upVotes();

    int downVotes();

    List<TagWithCount> tags();

    List<Tag> interestingTags();

    List<Tag> ignoredTags();
}
