package org.elbraulio.rosgh.iterator;

import org.elbraulio.rosgh.dom.RosQuestionsPagedDom;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;
import org.elbraulio.rosgh.tools.LastRosAnswersPage;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IteratePagedContentTest {
    final String webUrl =
            "https://answers.ros.org/questions/scope:all/sort:age-desc/page:1";

    @Test @Ignore
    public void iterateOverAllPages() throws IOException {
        IteratePagedContent s = new IteratePagedContent<>(
                new IterateDomPages(
                        new RosQuestionsPagedDom(),
                        1,
                        new LastRosAnswersPage(
                                Jsoup.connect(this.webUrl).get()
                        ).value(),
                        new IterateByQuestionLinks()
                )
        );
        while (s.hasNext()){
            System.out.println(s.next());
        }
    }
}