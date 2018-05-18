package tag;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosTagTest {

    @Test
    public void name() {
        assertThat(
                new RosTag("tagName").name(),
                is("tagName")
        );
    }

    @Test
    public void equals() {
        assertThat(
                new RosTag("aTag"),
                is(new RosTag("aTag"))
        );
    }

    @Test
    public void notEquals() {
        assertThat(
                new RosTag("aTag"),
                not(new RosTag("anotherTag"))
        );
    }

    @Test
    public void printToString() {
        assertThat(
                new RosTag("aTag").toString(),
                is("aTag")
        );
    }
}