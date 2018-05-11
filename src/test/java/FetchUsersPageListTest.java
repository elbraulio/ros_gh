import resources.FakeLoadPage;
import fetch.UserLinks;
import fetch.UserListPages;
import org.junit.Test;
import tools.IteratorAsList;

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
}
