package iterator;

import question.RqRosQuestion;
import tools.LastRosQuestionPage;

import javax.json.JsonArray;
import java.io.IOException;
import java.util.Iterator;

// TODO: 13-07-18 this class shouldn't exist, use IteratePagedContent instead.
/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class IterateApiQuestionPage implements Iterator<JsonArray> {

    private final int to;
    private int currentPage;

    public IterateApiQuestionPage() throws IOException {
        this(1, new LastRosQuestionPage().value());
    }

    public IterateApiQuestionPage(int from, int to) {

        this.to = to;
        this.currentPage = from;
    }

    @Override
    public boolean hasNext() {
        return this.currentPage < this.to;
    }

    @Override
    public JsonArray next() {
        try {
            return new RqRosQuestion(this.currentPage).jsonArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: 13-07-18 this is an design error
        return null;
    }
}
