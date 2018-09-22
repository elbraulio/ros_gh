package question;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class AnswerTest {
    private final int author = 33;
    private final String type = "comment";
    private final String date = "21/sep/2018";
    private final int votes = 0;
    private final boolean isAccepted = false;
    private Answer instance;

    @Before
    public void setUp(){
        instance = new Answer(author, type, date, votes, isAccepted);
    }

    @Test
    public void equals(){
        assertEquals(
                new Answer(33, "", "", 1, true),
                instance
        );
    }

    @Test
    public void allMethods(){
        assertEquals(author, instance.author());
        assertEquals(type, instance.type());
        assertEquals(date, instance.date());
        assertEquals(votes, instance.votes());
        assertEquals(isAccepted, instance.isAccepted());
    }
}