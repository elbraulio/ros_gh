package question;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import tools.QuestionIdFromUrl;

import java.io.IOException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultRosDomQuestion implements RosDomQuestion {
    private final Document document;

    public DefaultRosDomQuestion(int id) throws IOException {
        this(
                Jsoup.connect(
                        "https://answers.ros.org/question/" + id
                ).get()
        );
    }

    public DefaultRosDomQuestion(Document document) {
        this.document = document;
    }

    @Override
    public List<Answer> participants() {
        return new Participants(this.document).asList();
    }

    @Override
    public int votes() {
        return Integer.parseInt(
                this.document.select(
                        "div#question-vote-number-" +
                                new QuestionIdFromUrl(
                                        this.document.location()
                                ).id()
                ).text()
        );
    }
}
