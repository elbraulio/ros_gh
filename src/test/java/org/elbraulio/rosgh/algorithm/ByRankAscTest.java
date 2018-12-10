package org.elbraulio.rosgh.algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ByRankAscTest {
    @Test
    public void sort() {
        List<Aspirant> sorted = Arrays.asList(
                new OwnAspirant(-0.2),
                new OwnAspirant(-0.01),
                new OwnAspirant(0d),
                new OwnAspirant(0d),
                new OwnAspirant(1d),
                new OwnAspirant(2.3)
        );
        List<Aspirant> messy = Arrays.asList(
                new OwnAspirant(0d),
                new OwnAspirant(-0.2),
                new OwnAspirant(0d),
                new OwnAspirant(1d),
                new OwnAspirant(-0.01),
                new OwnAspirant(2.3)
        );
        List<Aspirant> byRank = new ByRankAsc().orderedList(messy);
        for (int i = 0; i < sorted.size(); i++) {
            assertThat(
                    String.valueOf(sorted.get(i).rank()),
                    is(String.valueOf(byRank.get(i)))
            );
        }
    }

    class OwnAspirant implements Aspirant {

        private final double rank;

        OwnAspirant(double rank) {
            this.rank = rank;
        }

        @Override
        public double rank() {
            return this.rank;
        }

        @Override
        public int id() {
            return 0;
        }

        @Override
        public String toString() {
            return String.valueOf(this.rank);
        }
    }
}
