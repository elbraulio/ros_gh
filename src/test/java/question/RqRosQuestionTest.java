package question;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RqRosQuestionTest {
    @Test
    public void network() throws IOException {
        assertEquals(50, new RqRosQuestion(2).jsonArray().size());
    }
}