import dom.RosQuestionsPagedDom;
import iterator.IterateByQuestionLinks;
import iterator.IterateDomPages;
import iterator.IteratePagedContent;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;
import question.DefaultRosDomQuestion;
import tools.LastRosAnswersPage;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class FetchAnswersTest {

    final String root = "https://answers.ros.org";

    private Iterator<String> makeIteratorForTest() throws IOException {
        final int initialPage = 1;
        final String rootDom = root + "/questions/scope:all/sort:age-desc/";
        return new IteratePagedContent(
                new IterateDomPages(
                        new RosQuestionsPagedDom(),
                        initialPage,
                        new LastRosAnswersPage(
                                Jsoup.connect(rootDom).get()
                        ).value(),
                        new IterateByQuestionLinks()
                )
        );
    }

    @Ignore
    @Test
    public void fetchAnswers() throws IOException {
        // to create the db, follow instructions on resources/sqlite/README.md
        final Iterator<String> contentLinks = makeIteratorForTest();
        while (contentLinks.hasNext()) {
            System.out.println(
                    new DefaultRosDomQuestion(
                            Jsoup.connect(root + contentLinks.next()).get()
                    )
            );
        }
    }
}
