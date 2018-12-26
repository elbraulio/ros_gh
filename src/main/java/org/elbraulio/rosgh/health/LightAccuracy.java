package org.elbraulio.rosgh.health;

import org.elbraulio.rosgh.algorithm.Aspirant;
import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.tag.Tag;

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
        double rank = 1d;
        int ghUserAccepted = 0;
        try {
            ghUserAccepted = new FecthGhUserAccepted(question.questionId(),
                    this.connection).id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = aspirants.size() - 1; i >= 0; i--) {
            if (aspirants.get(i).id() == ghUserAccepted) {
                break;
            }
            rank++;
        }
        if(rank < aspirants.size()*0.5) {
            return 1d; // tp
        } else if(rank == aspirants.size() + 1){
            return 2d; // fn
        } else {
            return 3d; // fp
        }
    }

    @Override
    public double recall(List<Aspirant> aspirants, TaggedItem question) {
        double rank = 1d;
        int ghUserAccepted = 0;
        try {
            ghUserAccepted = new FecthGhUserAccepted(question.questionId(),
                    this.connection).id();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = aspirants.size() - 1; i >= 0; i--) {
            if (aspirants.get(i).id() == ghUserAccepted) {
                break;
            }
            rank++;
        }
        if(rank < aspirants.size()*0.5) {
            return 1d; // tp
        } else if(rank == aspirants.size() + 1){
            return 2d; // fn
        } else {
            return 3d; // fp
        }
    }

    @Override
    public double mrr(List<Aspirant> aspirants, TaggedItem question) {
        double rank = rank(aspirants, question);
        return 1d / rank;
    }

    private double rank(List<Aspirant> aspirants, TaggedItem question) {
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
        return rank;
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
