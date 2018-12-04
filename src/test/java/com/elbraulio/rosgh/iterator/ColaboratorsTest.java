package com.elbraulio.rosgh.iterator;

import com.jcabi.github.RtGithub;
import com.elbraulio.rosgh.github.GhColaborator;
import org.junit.Ignore;
import org.junit.Test;
import com.elbraulio.rosgh.tools.CanRequest;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ColaboratorsTest {
    @Test @Ignore
    public void findBraulio() throws IOException, InterruptedException {
        List<GhColaborator> list = new Colaborators(
                "elbraulio/ros_gh",
                new CanRequest(60),
                new RtGithub()
        ).colaboratorList();
        assertTrue(list.size() > 1);
        assertTrue(
                list.contains(new GhColaborator("elbraulio", 0))
        );
    }
}