package examples.devrec;

import org.elbraulio.rosgh.algorithm.TaggedItem;
import org.elbraulio.rosgh.question.Answer;
import org.elbraulio.rosgh.tag.Tag;

import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class TaggedQuestion implements TaggedItem {
    private final int questionId;
    private final String title;
    private final int author;
    private final long addedAt;
    private final String summary;
    private final String url;
    private final int lastActivityBy;
    private final int viewCount;
    private final int votes;
    private final List<Tag> tags;
    private final List<Answer> answers;
    private final List<Integer> repos;

    public TaggedQuestion(List<Integer> repos, int questionId, String title,
                          int author,
                          long addedAt,
                          String summary, String url, int lastActivityBy,
                          int viewCount, int votes, List<Tag> tags,
                          List<Answer> answers) {
        this.repos = repos;
        this.questionId = questionId;
        this.title = title;
        this.author = author;
        this.addedAt = addedAt;
        this.summary = summary;
        this.url = url;
        this.lastActivityBy = lastActivityBy;
        this.viewCount = viewCount;
        this.votes = votes;
        this.tags = tags;
        this.answers = answers;
    }

    @Override
    public int questionId() {
        return questionId;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int author() {
        return author;
    }

    @Override
    public long addedAt() {
        return addedAt;
    }

    @Override
    public String summary() {
        return summary;
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public int lastActivityBy() {
        return lastActivityBy;
    }

    @Override
    public int viewCount() {
        return viewCount;
    }

    @Override
    public int votes() {
        return votes;
    }

    @Override
    public List<Tag> tags() {
        return tags;
    }

    @Override
    public List<Answer> answers() {
        return answers;
    }

    @Override
    public List<Integer> repos() {
        return repos;
    }
}
