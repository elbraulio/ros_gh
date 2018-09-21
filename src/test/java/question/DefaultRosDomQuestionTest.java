package question;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultRosDomQuestionTest {
    @Test
    public void network() throws IOException {
        final DefaultRosDomQuestion dom = new DefaultRosDomQuestion(9040);
        assertEquals(2, dom.participants().size());
        assertEquals(5, dom.votes());
    }
}