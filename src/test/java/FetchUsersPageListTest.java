import dom.PagedDom;
import dom.RosPagedDom;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import resources.FakePagedDom;
import iterator.IteratePagedUsersLinks;
import iterator.IterateUserPages;
import org.junit.Test;
import tools.IteratorAsList;
import tools.LastRosUserPage;
import user.RosDomUser;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FetchUsersPageListTest {

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
        final int initialPage = 1;
        final String root = "https://answers.ros.org";
        final Document usersPage = Jsoup.connect(root + "/users/").get();
        final Iterator<String> usersLinks =
                new IteratePagedUsersLinks(
                        new IterateUserPages(
                                new RosPagedDom(),
                                initialPage,
                                new LastRosUserPage(usersPage).value()
                        )
                );

        while (usersLinks.hasNext()) {
            final String next = usersLinks.next();
            System.out.println(
                    new RosDomUser(Jsoup.connect(root + next).get())
            );
        }
    }
}
