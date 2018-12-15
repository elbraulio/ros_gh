package com.elbraulio.rosgh.health;

import com.elbraulio.rosgh.algorithm.Aspirant;
import com.elbraulio.rosgh.algorithm.TaggedItem;
import com.elbraulio.rosgh.tag.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class LightAccuracy implements Accuracy {

    private final Connection connection;

    public LightAccuracy(Connection connection) {

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
        double rank = 1d;
        int minAccepted = (question.tags().size() / 2);
        minAccepted = minAccepted == 0 ? 1 : minAccepted;
        for (int i = aspirants.size() - 1; i >= 0; i--) {
            try {
                if (
                        containsAtLeast(
                                new FetchTagByUser(
                                        aspirants.get(i).id(), connection
                                ).list(),
                                question.tags(),
                                minAccepted
                        )
                ) {
                    break;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rank++;
        }
        return 1d / rank;
    }

    private boolean containsAtLeast(List<Tag> userTags,
                                    List<Tag> questionTags,
                                    int minAccepted) {
        int matched = 0;
        for (Tag tag :
                questionTags) {
            if (userTags.contains(tag)) {
                if (++matched == minAccepted)
                    return true;
            }
        }
        return false;
    }
}
