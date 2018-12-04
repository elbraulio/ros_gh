package com.elbraulio.rosgh.github;

import com.jcabi.github.RtGithub;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosPackageTest {
    @Test @Ignore
    public void network() throws IOException {
        final RosPackage pkge = new RosPackage(
                "ros_gh",
                "https://github.com/elbraulio/ros_gh.git",
                "source"
        );
        final GhRepo repo = pkge.asRepo(new RtGithub());
        assertEquals("elbraulio/ros_gh", repo.fullName());
        assertEquals("ros_gh", repo.name());
        assertEquals("elbraulio", repo.owner());
        assertEquals(
                "Answerers Recommendation System for ROS Answers",
                repo.description()
        );
        assertEquals(-1, repo.dbId());
        assertEquals("source", pkge.source());
        assertEquals("ros_gh", pkge.name());
    }
}