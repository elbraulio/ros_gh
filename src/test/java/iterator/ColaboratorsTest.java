package iterator;

import com.jcabi.github.RtGithub;
import github.GhColaborator;
import github.GhRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import tools.CanRequest;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ColaboratorsTest {
    @Test
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