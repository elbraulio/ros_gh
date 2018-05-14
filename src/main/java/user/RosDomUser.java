package user;

import org.jsoup.nodes.Document;
import tag.*;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosDomUser implements RosUser {
    private final Document document;

    public RosDomUser(final Document document) {
        this.document = document;
    }

    @Override
    public String name() {
        return this.document.select(".section-title")
                .get(0).text().split("'")[0];
    }

    @Override
    public int upVotes() {
        return Integer.parseInt(
                this.document.select(".up-votes")
                        .get(0).text()
        );
    }

    @Override
    public int downVotes() {
        return Integer.parseInt(
                this.document.select(".down-votes")
                        .get(0).text()
        );
    }

    @Override
    public List<Tag> interestingTags() {
        return new TagList(this.document, new InterestingTags()).asList();
    }

    @Override
    public List<Tag> ignoredTags() {
        return new TagList(this.document, new IgnoredTags()).asList();
    }

    @Override
    public List<TagWithCount> tags() {
        return new TagWithCountList(this.document, new NormalTags()).asList();
    }

    @Override
    public String toString(){
        return "name: " + this.name() + "\n" +
                "upVotes: " + this.upVotes() + "\n" +
                "downVotes: " + this.downVotes() + "\n" +
                "tags: " + this.tags() + "\n" +
                "interestingTag: " + this.interestingTags() + "\n" +
                "ignoredtag: " + this.ignoredTags() + "\n";
    }
}
