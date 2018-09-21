package tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class QuestionIdFromUrlTest {
    @Test
    public void realCase() {
        // TODO: 21-09-18 number as String?
        assertEquals(
                "9040",
                new QuestionIdFromUrl(
                        "https://answers.ros.org/question/9040/which-" +
                                "data-types-does-tf-use/"
                ).id()
        );
    }
}