package examples.devrec;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.ByRankDesc;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Launcher {

    private static final Logger logger = Logger.getLogger(Launcher.class);

    public static void main(String... args) {
        Algorithm devrec = new Devrec();
        List<Aspirant> sorted = new ByRankDesc().orderedList(
                devrec.aspirants(null)
        );
        logger.info(sorted);
    }
}
