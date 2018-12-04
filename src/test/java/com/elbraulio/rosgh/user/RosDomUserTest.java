package com.elbraulio.rosgh.user;

import com.elbraulio.rosgh.tag.Tag;
import com.elbraulio.rosgh.tag.WithCount;
import org.jsoup.Jsoup;
import org.junit.Test;
import com.elbraulio.rosgh.tag.RosTag;
import com.elbraulio.rosgh.tag.TagWithCount;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosDomUserTest {

    private final String fakePerfilPath =
            "src/test/resources/userPage1.txt";
    private final String fakeEmptyPerfilPath =
            "src/test/resources/userPage2.txt";

    @Test
    public void readNameFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).name(),
                is("Humpelstilzchen")
        );
    }

    @Test
    public void readUpVotesFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).upVotes(),
                is(39)
        );
    }

    @Test
    public void readDownVotesFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).downVotes(),
                is(2)
        );
    }

    @Test
    public void readTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).tags().toArray(new TagWithCount[5]),
                is(
                        new TagWithCount[]{
                                new WithCount(
                                        new RosTag("navigation"),
                                        44
                                ),
                                new WithCount(
                                        new RosTag("kinetic"),
                                        41
                                ),
                                new WithCount(
                                        new RosTag("move_base"),
                                        40
                                ),
                                new WithCount(
                                        new RosTag("ROS"),
                                        40
                                ),
                                new WithCount(
                                        new RosTag("amcl"),
                                        24
                                ),
                        }
                )
        );
    }

    @Test
    public void readIterestingTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).interestingTags().toArray(new Tag[5]),
                is(
                        new Tag[]{
                                new RosTag("Kinect"),
                                new RosTag("gmapping"),
                                new RosTag("openni"),
                                new RosTag("xtion"),
                                new RosTag("wifi"),
                        }
                )
        );
    }

    @Test
    public void readIgnoredTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakePerfilPath
                                ),
                                "UTF-8"
                        )
                ).ignoredTags().toArray(new Tag[1]),
                is(
                        new Tag[]{
                                new RosTag("bfl"),
                        }
                )
        );
    }

    @Test
    public void readEmptyUpVotesFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).upVotes(),
                is(0)
        );
    }

    @Test
    public void readEmptyDownVotesFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).downVotes(),
                is(0)
        );
    }

    @Test
    public void readEmptyTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).tags().toArray(new TagWithCount[0]),
                is(
                        new TagWithCount[]{}
                )
        );
    }

    @Test
    public void readEmptyIterestingTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).interestingTags().toArray(new Tag[0]),
                is(
                        new Tag[]{}
                )
        );
    }

    @Test
    public void readEmptyIgnoredTagsFromDom() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).ignoredTags().toArray(new Tag[0]),
                is(
                        new Tag[]{}
                )
        );
    }

    @Test
    public void asString() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.parse(
                                new File(
                                        this.fakeEmptyPerfilPath
                                ),
                                "UTF-8"
                        )
                ).toString(),
                is(
                        "name: elbraulio\n" +
                                "upVotes: 0\n" +
                                "downVotes: 0\n" +
                                "tags: []\n" +
                                "interestingTag: []\n" +
                                "ignoredtag: []\n" +
                                "karma: 1\n" +
                                "location: \n" +
                                "description: \n" +
                                "realName: \n" +
                                "age: null\n" +
                                "avatarUrl: //www.gravatar.com/avatar/977aea2b1931661bb877be29d8a28ef3?s=128&d=identicon&r=PG\n" +
                                "joinedAt: 1523875495\n" +
                                "lastSeenAt: 1526143144\n"
                )
        );
    }
}
