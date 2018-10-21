package org.elbraulio.rosgh.question;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class CommentVoteCountTest {
    @Test
    public void empty(){
        assertEquals(0, new CommentVoteCount("").count());
    }

    @Test
    public void notEmpty(){
        assertEquals(1, new CommentVoteCount("1").count());
    }
}