import dom.PagedDom;
import dom.RosPagedDom;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import resources.FakePagedDom;
import iterator.IteratePagedUsersLinks;
import iterator.IterateUserPages;
import org.junit.Test;
import resources.SaveIntoSqliteDb;
import resources.SqliteConnection;
import tools.IteratorAsList;
import tools.LastRosUserPage;
import user.RosDomUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchUsersPageListTest {

    final String root = "https://answers.ros.org";

    @Test
    public void fetchAllPages() {
        final int initialPage = 1;
        final int finalPage = 2;
        assertThat(
                new IteratorAsList<>(
                        new IteratePagedUsersLinks(
                                new IterateUserPages(
                                        new FakePagedDom(),
                                        initialPage,
                                        finalPage
                                )
                        )
                ).asList().toArray(new String[6]),
                is(
                        new String[]{
                                "/users/3/tfoote/",
                                "/users/1034/ahendrix/",
                                "/users/5184/gvdhoorn/",
                                "/users/3925/jeremy-zoss/",
                                "/users/1108/martin-peris/",
                                "/users/127/mac/",
                        }
                )
        );
    }

    @Test
    @Ignore
    public void fetchFirstNUsersLinks() throws IOException {
        final PagedDom pagedDom = new RosPagedDom();
        final int initialPage = 1;
        final Document firstPage = pagedDom.page(1);
        final List<String> links = new IteratorAsList<>(
                new IteratePagedUsersLinks(
                        new IterateUserPages(
                                pagedDom,
                                initialPage,
                                new LastRosUserPage(
                                        firstPage
                                ).value()
                        )
                )
        ).take(100);

        System.out.println(links);

        assertThat(
                links.size(),
                is(100)
        );
    }

    @Test
    @Ignore
    public void fetchAndPrintAllUsers() throws IOException {
        final Iterator<String> usersLinks = makeIteratorForTest();

        while (usersLinks.hasNext()) {
            final String next = usersLinks.next();
            System.out.println(
                    new RosDomUser(Jsoup.connect(root + next).get())
            );
        }
    }

    private Iterator<String> makeIteratorForTest() throws IOException {
        final int initialPage = 1;
        final Document usersPage = Jsoup.connect(root + "/users/").get();
        return new IteratePagedUsersLinks(
                new IterateUserPages(
                        new RosPagedDom(),
                        initialPage,
                        new LastRosUserPage(usersPage).value()
                )
        );
    }

    @Ignore
    @Test
    public void saveAllData()
            throws IOException, SQLException, ClassNotFoundException {
        //final Iterator<String> usersLinks = makeIteratorForTest();
        IteratePagedUsersLinks usersLinks =
                new IteratePagedUsersLinks(
                        new IterateUserPages(
                                new RosPagedDom(),
                                507,
                                696
                        )
                );
        // to create the db, follow instructions on resources/sqlite/README.md
        try (
                final Connection connection = new SqliteConnection(
                        "src/test/java/resources/sqlite/test.db"
                ).connection()
        ) {
            int count = 15192;
            while(usersLinks.hasNext()){
                final String next = usersLinks.next();
                if(next.equals("/users/28573/peermer/")){
                    break;
                }
            }
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