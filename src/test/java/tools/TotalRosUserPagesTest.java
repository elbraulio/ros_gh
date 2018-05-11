package tools;

import org.junit.Test;
import resources.FakeLoadPage;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class TotalRosUserPagesTest {

    @Test
    public void findTheLastPageFromTheFirstPage() throws IOException {
        assertThat(
                new TotalRosUserPages(
                        new FakeLoadPage().page(1)
                ).totalPages(),
                is(693)
        );
    }
}