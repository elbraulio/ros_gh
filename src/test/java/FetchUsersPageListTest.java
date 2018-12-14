import com.elbraulio.rosgh.iterator.IterateByUserLinks;
import com.elbraulio.rosgh.iterator.IterateDomPages;
import com.elbraulio.rosgh.iterator.IteratePagedContent;
import com.elbraulio.rosgh.tools.IteratorAsList;
import org.junit.Test;
import testtools.FakePagedDom;

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
                        new IteratePagedContent<>(
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
}
