package question;

import org.jsoup.nodes.Document;
import tag.QuestionTags;
import tag.Tag;
import tools.DistintcElements;
import tools.HtmlContent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultRosDomQuestion implements RosDomQuestion {
    private final Document document;

    public DefaultRosDomQuestion(Document document) {
        this.document = document;
    }

    @Override
    public List<String> collaborators() {
        final List<String> participants = new LinkedList<>();
        this.document.select("div.user-info > a[href]")
                .forEach(
                        e -> participants.add(e.text())
                );
        this.document.select("a.author")
                .forEach(
                        e -> participants.add(e.text())
                );
        return new DistintcElements<>(participants).list();
    }
}
