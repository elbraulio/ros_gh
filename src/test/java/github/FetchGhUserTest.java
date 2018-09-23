package github;

import com.jcabi.github.RtGithub;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class FetchGhUserTest {
    @Test @Ignore
    public void fetch() throws IOException {
        assertThat(
             new FetchGhUser(new RtGithub(), "elbraulio").ghUser().url(),
                is("https://github.com/elbraulio")
        );
    }
}