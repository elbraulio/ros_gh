package examples.devrec;

import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.ByRankDesc;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Launcher {
    public static void main(String... args) {
        Algorithm devrec = new Devrec();
        List<Aspirant> sorted = new ByRankDesc().orderedList(
                devrec.aspirants(null)
        );
        System.out.println(sorted);
    }
}
