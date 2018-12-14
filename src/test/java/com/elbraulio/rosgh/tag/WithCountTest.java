package com.elbraulio.rosgh.tag;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class WithCountTest {

    @Test
    public void count() {
        assertThat(
            new WithCount("aTag", 1).count(),
            is(1)
        );
    }

    @Test
    public void name() {
        assertThat(
                new WithCount("aTag", 1).name(),
                is("aTag")
        );
    }

    @Test
    public void equals() {
        assertThat(
                new WithCount("aTag", 1),
                is(new WithCount("aTag", 1))
        );
    }

    @Test
    public void notEquals() {
        assertThat(
                new WithCount("aTag", 1),
                CoreMatchers.not(new WithCount("aTag", 2))
        );
    }

    @Test
    public void printToString() {
        assertThat(
                new WithCount("aTag", 1).toString(),
                is("aTag x 1")
        );
    }
}