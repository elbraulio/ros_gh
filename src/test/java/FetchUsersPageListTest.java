import fetch.LoadPage;
import fetch.RosLoadPage;
import org.junit.Ignore;
import resources.FakeLoadPage;
import iterator.UserLinks;
import iterator.UserListPages;
import org.junit.Test;
import tools.IteratorAsList;
import tools.TotalRosUserPages;

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
        assertThat(
                new IteratorAsList<>(
                        new UserLinks(
                                new UserListPages(
                                        new FakeLoadPage(),
                                        1,
                                        2
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
    public void fetchFirstTenUsersLinks() throws IOException {
        final LoadPage loadPage = new RosLoadPage();
        final List<String> links = new IteratorAsList<>(
                new UserLinks(
                        new UserListPages(
                                loadPage,
                                1,
                                new TotalRosUserPages(
                                        loadPage.page(1)
                                ).totalPages()
                        )
                )
        ).take(100);

        System.out.println(links);

        assertThat(
                links.size(),
                is(100)
        );
    }
}
