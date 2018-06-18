import dom.RosQuestionsPagedDom;
import iterator.IteratePagedContent;
import iterator.IterateDomPages;
import iterator.IterateByQuestionLinks;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;
import resources.SaveIntoSqliteDb;
import resources.SqliteConnection;
import tools.LastRosAnswersPage;
import user.RosDomUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
    public void fetchAnswers()
            throws IOException, SQLException, ClassNotFoundException {
        // to create the db, follow instructions on resources/sqlite/README.md
        final Iterator<String> usersLinks = makeIteratorForTest();
        try (
                final Connection connection = new SqliteConnection(
                        "src/test/java/resources/sqlite/test.db"
                ).connection()
        ) {
            int count = 1;
            while (usersLinks.hasNext()) {
                final String next = usersLinks.next();
                System.out.println(
                        "Processing user " + count++ + " ->" + next
                );
                new SaveIntoSqliteDb(
                        new RosDomUser(Jsoup.connect(root + next).get()),
                        connection
                ).execute();
            }
        }
    }
}
