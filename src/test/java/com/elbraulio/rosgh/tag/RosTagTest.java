package com.elbraulio.rosgh.tag;

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
                new RosTag("tagName",0).name(),
                is("tagName")
        );
    }

    @Test
    public void equals() {
        assertThat(
                new RosTag("aTag", 0),
                is(new RosTag("aTag", 0))
        );
    }

    @Test
    public void notEquals() {
        assertThat(
                new RosTag("aTag", 0),
                not(new RosTag("anotherTag", 0))
        );
    }

    @Test
    public void printToString() {
        assertThat(
                new RosTag("aTag", 0).toString(),
                is("aTag")
        );
    }
}