package dom;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosUserPagedDomTest {
    @Test
    @Ignore
    public void fetchWebDom() throws IOException {
        assertThat(
                new RosUserPagedDom()
                        .page(1)
                        .select("title").text(),
                is("Users - ROS Answers: Open Source Q&A Forum")
        );
    }
}