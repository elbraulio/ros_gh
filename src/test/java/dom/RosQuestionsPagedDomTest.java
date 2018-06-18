package dom;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosAnswersPagedDomTest {
    @Test
    @Ignore
    public void fetchWebDom() throws IOException {
        assertThat(
                new RosAnswersPagedDom()
                        .page(1)
                        .select("title").text(),
                is("Questions - ROS Answers: Open Source Q&A Forum")
        );
    }
}