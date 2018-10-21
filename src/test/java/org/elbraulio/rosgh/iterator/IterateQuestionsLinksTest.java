package org.elbraulio.rosgh.iterator;

import org.jsoup.Jsoup;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateQuestionsLinksTest {

    final String webUrl =
            "https://answers.ros.org/questions/scope:all/sort:age-desc/page:1";

    @Test
    public void fetchAtLeast2Pages() throws IOException {
        IterateQuestionsLinks iterate = new IterateQuestionsLinks(
                Jsoup.connect(this.webUrl).get()
        );
        int count = 0;
        while (iterate.hasNext()){
            count++;
            if(count > 100) break;
        }
        assertThat(count, is(101));
    }
}