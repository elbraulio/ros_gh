package github;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhColaboratorTest {

    @Test
    public void justStoreData() {
        GhColaborator ghColaborator = new GhColaborator("author", 1);
        assertThat(
                ghColaborator.commits(),
                is(1)
        );
        assertThat(
                ghColaborator.login(),
                is("author")
        );
    }
}