package tools;

import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class LastRosAnswersPageTest {

    final String root = "https://answers.ros.org";
    final String rootDom = root + "/questions/scope:all/sort:age-desc/";
    @Test @Ignore
    public void findLastPage() throws IOException {
        // TODO: 18-06-18 make test with static dom from resources
        assertThat(
                new LastRosAnswersPage(
                        Jsoup.connect(rootDom).get()
                ).value(),
                is(811)
        );
    }
}