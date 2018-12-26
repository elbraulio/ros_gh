package org.elbraulio.rosgh.health;

import org.apache.log4j.Logger;
import org.elbraulio.rosgh.algorithm.Algorithm;
import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.ByRankAsc;
import org.elbraulio.rosgh.algorithm.TaggedItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class DefaultHealthCheck implements HealthCheck {

    private final Logger logger = Logger.getLogger(DefaultHealthCheck.class);
    private final Accuracy accuracy;
    private final Algorithm algorithm;
    private final List<TaggedItem> sample;

    public DefaultHealthCheck(Accuracy accuracy, Algorithm algorithm,
                              List<TaggedItem> sample) {

        this.accuracy = accuracy;
        this.algorithm = algorithm;
        this.sample = sample;
    }

    @Override
    public void logHealth() {
        final List<Result> results = new ArrayList<>(this.sample.size());
        this.sample.forEach(
                question -> {
                    List<Aspirant> aspirants =
                            new ByRankAsc().orderedList(algorithm.aspirants(question));
                    results.add(
                            new Result(
                                    this.accuracy.precision(
                                            aspirants, question
                                    ),
                                    this.accuracy.recall(aspirants, question),
                                    this.accuracy.mrr(aspirants, question)
                            )
                    );
                }
        );
        double tp = 0;
        double fp = 0;
        double fn = 0;
        this.logger.info("-------- Average from " + results.size() + " --------");
        this.logger.info("Precision:");
        for(Result r : results){
            fp += r.precision == 3 ? 1 : 0;
            tp += r.precision == 1 ? 1 : 0;
            fn += r.precision == 2 ? 1 : 0;
        }
        this.logger.info(
                tp / (tp + fp)
        );
        this.logger.info("Recall:");
        this.logger.info(
                tp / (tp + fn)
        );
        this.logger.info("MRR:");
        this.logger.info(
                Arrays.stream(results.toArray(new Result[0])).reduce(
                        new Result(0, 0, 0),
                        (r1, r2) -> new Result(
                                0, 0, r1.mrr + r2.mrr
                        )
                ).mrr / results.size()
        );
    }

    private final class Result {

        private final double precision;
        private final double recall;
        private final double mrr;

        public Result(double precision, double recall, double mrr) {

            this.precision = precision;
            this.recall = recall;
            this.mrr = mrr;
        }
    }
}
