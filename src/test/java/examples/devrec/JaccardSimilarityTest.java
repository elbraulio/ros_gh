package examples.devrec;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class JaccardSimilarityTest {
    @Test
    public void fullMatch(){
        MatcherAssert.assertThat(
                new JaccardSimilarity(
                        0,1,new double[][]{{1d,1d},{1d, 1d}}
                        ).val(),
                Matchers.is(1d)
        );
    }
}