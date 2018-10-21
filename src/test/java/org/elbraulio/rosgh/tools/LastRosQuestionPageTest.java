package org.elbraulio.rosgh.tools;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class LastRosQuestionPageTest {
    @Test
    public void lastAPiPage() throws IOException {
        assertTrue(new LastRosQuestionPage().value() > 845);
    }
}