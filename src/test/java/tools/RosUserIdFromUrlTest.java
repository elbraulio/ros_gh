package tools;

import org.hamcrest.Matchers;
import org.jsoup.Jsoup;
import org.junit.Test;
import user.RosDomUser;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosUserIdFromUrlTest {
    @Test
    public void takeIdFromUrl() {
        assertThat(
                new RosUserIdFromUrl(
                        "https://answers.ros.org/users/3/tfoote/"
                ).id(),
                Matchers.is(3)
        );
    }

    @Test
    public void supportLongestId() {
        assertThat(
                new RosUserIdFromUrl(
                        "https://answers.ros.org/users/36340/shahbaz-khan/"
                ).id(),
                Matchers.is(36340)
        );
    }

    @Test
    public void realUserIdCase() throws IOException {
        assertThat(
                new RosDomUser(
                        Jsoup.connect(
                                "https://answers.ros.org/users/3/tfoote/"
                        ).get()
                ).rosId(),
                Matchers.is(3)
        );
    }
}