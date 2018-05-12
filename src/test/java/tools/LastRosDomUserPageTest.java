package tools;

import org.junit.Test;
import resources.FakePagedDom;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class LastRosDomUserPageTest {

    @Test
    public void findTheLastPageFromTheFirstPage() throws IOException {
        assertThat(
                new LastRosUserPage(
                        new FakePagedDom().page(1)
                ).value(),
                is(693)
        );
    }
}