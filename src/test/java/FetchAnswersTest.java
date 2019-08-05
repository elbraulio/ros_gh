import org.elbraulio.rosgh.dom.RosQuestionsPagedDom;
import org.elbraulio.rosgh.iterator.IterateApiQuestionPage;
import org.elbraulio.rosgh.iterator.IterateByQuestionLinks;
import org.elbraulio.rosgh.iterator.IterateDomPages;
import org.elbraulio.rosgh.iterator.IteratePagedContent;
import org.elbraulio.rosgh.question.ApiRosQuestion;
import org.elbraulio.rosgh.question.DefaultApiRosQuestion;
import org.elbraulio.rosgh.question.DefaultRosDomQuestion;
import org.elbraulio.rosgh.question.InsertQuestionWithExtras;
import org.elbraulio.rosgh.tools.LastRosAnswersPage;
import org.elbraulio.rosgh.tools.SqliteConnection;
import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;

import javax.json.JsonArray;
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
        return new IteratePagedContent<>(
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
    public void fetchDomAnswers() throws IOException {
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

    @Test
    @Ignore
    public void fetchApiAnswers() throws IOException, SQLException, ClassNotFoundException {
        final Iterator<JsonArray> iterable = new IterateApiQuestionPage();
        final String dbPath = "./src/test/resources/sqlite/test.db";
        final Connection connection = new SqliteConnection(dbPath).connection();
        iterable.forEachRemaining(
                jsonArray -> {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        final ApiRosQuestion question =
                                new DefaultApiRosQuestion(
                                        jsonArray.getJsonObject(i)
                                );
                        try {
                            System.out.println(question.url());
                            // general info
                            new InsertQuestionWithExtras(
                                    question,
                                    new DefaultRosDomQuestion(question.id())
                            ).execute(
                                    connection, -1
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            continue;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NullPointerException n) {
                            n.printStackTrace();
                            continue;
                        }
                    }
                }
        );
    }
}
