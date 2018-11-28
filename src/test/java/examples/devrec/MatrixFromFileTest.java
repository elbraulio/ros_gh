package examples.devrec;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class MatrixFromFileTest {
    @Test
    public void shortMatrix() throws IOException {
        MatcherAssert.assertThat(
                new MatrixFromFile(
                        "./src/test/resources/shortMatrix", 3
                ).matrix(),
                Matchers.arrayContaining(
                        new double[][]{
                                {1d, 2d, 3d},
                                {4d, 5d, 6d},
                                {7d, 8d, 9d},
                        }
                )
        );
    }
}