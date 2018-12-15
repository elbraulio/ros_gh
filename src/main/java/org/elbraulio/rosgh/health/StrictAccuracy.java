package org.elbraulio.rosgh.health;

import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.health.Accuracy;
import org.elbraulio.rosgh.health.FecthGhUserAccepted;
import org.elbraulio.rosgh.health.FetchTagByUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class StrictAccuracy implements Accuracy {
    private final Connection connection;

    public StrictAccuracy(Connection connection) {
        this.connection = connection;
    }

    @Override
    public double precision(List<Aspirant> aspirants, TaggedItem question) {
        return 0;
    }

    @Override
    public double recall(List<Aspirant> aspirants, TaggedItem question) {
        return 0;
    }

    @Override
    public double mrr(List<Aspirant> aspirants, TaggedItem question) {
        int ghUserAccepted = 0;
        try {
            ghUserAccepted = new FecthGhUserAccepted(question.questionId(),
                    this.connection).id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        double rank = 1d;
        for (int i = aspirants.size() - 1; i >= 0; i--) {
            if (aspirants.get(i).id() == ghUserAccepted) {
                break;
            }
            rank++;
        }
        return 1d / rank;
    }
}
