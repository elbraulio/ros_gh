package org.elbraulio.rosgh.user;

import org.elbraulio.rosgh.tag.Tag;
import org.elbraulio.rosgh.tag.TagWithCount;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public interface RosUser {
    String name();

    int upVotes();

    int downVotes();

    int rosId();

    int karma();

    String location();

    String description();

    String realName();

    Integer age();

    String avatarUrl();

    long joinedAt();

    long lastSeenAt();

    /* TODO: 18-05-18 Too many methods!
     * this 3 method should be one UserTag object returned by 1 method
     */

    List<TagWithCount> tags();

    List<Tag> interestingTags();

    List<Tag> ignoredTags();
}
