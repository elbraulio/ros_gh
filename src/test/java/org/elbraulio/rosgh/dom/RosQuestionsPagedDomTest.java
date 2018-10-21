package org.elbraulio.rosgh.dom;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosQuestionsPagedDomTest {
    @Test
    public void fetchWebDom() throws IOException {
        assertThat(
                new RosQuestionsPagedDom()
                        .page(1)
                        .select("title").text(),
                is("Questions - ROS Answers: Open Source Q&A Forum")
        );
    }
}