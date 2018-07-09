import dom.PagedDom;
import dom.RosUserPagedDom;
import iterator.IterateByUserLinks;
import iterator.IterateDomPages;
import iterator.IteratePagedContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import resources.FakePagedDom;
import tools.SaveIntoSqliteDb;
import tools.SqliteConnection;
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
                        new IteratePagedContent(
                                new IterateDomPages(
                                        new FakePagedDom(),
                                        initialPage,
                                        finalPage,
                                        new IterateByUserLinks()
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
        final PagedDom pagedDom = new RosUserPagedDom();
        final int initialPage = 1;
        final Document firstPage = pagedDom.page(1);
        final List<String> links = new IteratorAsList<>(
                new IteratePagedContent(
                        new IterateDomPages(
                                pagedDom,
                                initialPage,
                                new LastRosUserPage(
                                        firstPage
                                ).value(),
                                new IterateByUserLinks()
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
        return new IteratePagedContent(
                new IterateDomPages(
                        new RosUserPagedDom(),
                        initialPage,
                        new LastRosUserPage(usersPage).value(),
                        new IterateByUserLinks()
                )
        );
    }

    @Ignore
    @Test
    public void saveAllData()
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
