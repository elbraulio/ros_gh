package user;

import tag.Tag;
import tag.TagWithCount;

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
