package com.elbraulio.rosgh.user;

import com.elbraulio.rosgh.tag.*;
import com.elbraulio.rosgh.tools.RosUserIdFromUrl;
import org.jsoup.nodes.Document;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
    public int rosId() {
        return new RosUserIdFromUrl(this.document.location()).id();
    }

    @Override
    public int karma() {
        return Integer.parseInt(
                this.document.select(".scoreNumber")
                        .get(0).text().replace(",", "")
        );
    }

    @Override
    public String location() {
        return document.select("td:contains(location)")
                .next().text();
    }

    @Override
    public String description() {
        if (document.selectFirst(".user-about > p") != null)
            return document.selectFirst(".user-about > p").text();
        else return "";
    }

    @Override
    public String realName() {
        return document.select("td:contains(real name)")
                .next().text();
    }

    @Override
    public Integer age() {
        if (document.select("td:contains(age, years)")
                .next().hasText()) {
            return Integer.parseInt(
                    document.select("td:contains(age, years)")
                            .next().text()
            );
        } else return null;
    }

    @Override
    public String avatarUrl() {
        String url = document.selectFirst(".gravatar").attr("src");
        if (url.contains("nophoto.png")) return null;
        return url;
    }

    @Override
    public long joinedAt() {
        return LocalDateTime.parse(
                document.select("td:contains(member since)")
                        .next().select("strong > abbr").attr("title"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
        ).toEpochSecond(ZoneOffset.UTC);
    }

    @Override
    public long lastSeenAt() {
        return LocalDateTime.parse(
                document.select("td:contains(last seen)").next()
                        .select("strong > abbr").attr("title"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z")
        ).toEpochSecond(ZoneOffset.UTC);
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
    public String toString() {
        return "name: " + this.name() + "\n" +
                "upVotes: " + this.upVotes() + "\n" +
                "downVotes: " + this.downVotes() + "\n" +
                "tags: " + this.tags() + "\n" +
                "interestingTag: " + this.interestingTags() + "\n" +
                "ignoredtag: " + this.ignoredTags() + "\n" +
                "karma: " + this.karma() + "\n" +
                "location: " + this.location() + "\n" +
                "description: " + this.description() + "\n" +
                "realName: " + this.realName() + "\n" +
                "age: " + this.age() + "\n" +
                "avatarUrl: " + this.avatarUrl() + "\n" +
                "joinedAt: " + this.joinedAt() + "\n" +
                "lastSeenAt: " + this.lastSeenAt() + "\n";
    }
}
