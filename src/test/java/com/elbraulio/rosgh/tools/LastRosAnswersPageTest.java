package com.elbraulio.rosgh.tools;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class LastRosAnswersPageTest {

    final String root = "https://answers.ros.org";
    final String rootDom = root + "/questions/scope:all/sort:age-desc/";
    @Test
    public void findLastPage() throws IOException {
        // TODO: 18-06-18 make test with static dom from resources
        assertTrue(
                new LastRosAnswersPage(
                        Jsoup.connect(rootDom).get()
                ).value() > 845
        );
    }
}