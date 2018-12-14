import org.elbraulio.rosgh.dom.PagedDom;
import org.elbraulio.rosgh.dom.RosUserPagedDom;
import org.elbraulio.rosgh.iterator.IterateByUserLinks;
import org.elbraulio.rosgh.iterator.IterateDomPages;
import org.elbraulio.rosgh.iterator.IteratePagedContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Ignore;
import org.junit.Test;
import org.elbraulio.rosgh.tools.IteratorAsList;
import org.elbraulio.rosgh.tools.LastRosUserPage;
import org.elbraulio.rosgh.tools.SaveIntoSqliteDb;
import org.elbraulio.rosgh.tools.SqliteConnection;
import org.elbraulio.rosgh.user.RosDomUser;
import testtools.FakePagedDom;

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
