package iterator;

import dom.RosQuestionsPagedDom;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;
import tools.LastRosAnswersPage;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateQuestionsLinksTest {

    final String webUrl =
            "https://answers.ros.org/questions/scope:all/sort:age-desc/page:1";

    @Test @Ignore
    public void printQuestionsLinks() throws IOException {
        IterateQuestionsLinks iterate = new IterateQuestionsLinks(
                Jsoup.connect(this.webUrl).get()
        );
        while (iterate.hasNext()){
            System.out.println(iterate.next());
        }
    }
}