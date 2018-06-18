package question;

import org.jsoup.nodes.Document;
import tag.QuestionTags;
import tag.Tag;
import tools.HtmlContent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosDomQuestion implements RosQuestion {
    private final Document document;

    public RosDomQuestion(Document document) {
        this.document = document;
    }

    @Override
    public String title() {
        return this.document.select("div.post-content > h1")
                .get(0).getAllElements().get(1).text();
    }

    @Override
    public String author() {
        return this.document.select(
                "div.post-update-info > div.user-card"
        ).get(0).select("div.user-info > a")
                .text();
    }

    @Override
    public String date() {
        return this.document.select(
                "div.post-update-info > p > a > strong > abbr"
        ).get(0).attr("title");
    }

    @Override
    public List<Tag> tags() {
        return new QuestionTags(document).asList();
    }

    @Override
    public List<String> participants() {
        final List<String> participants = new LinkedList<>();
        this.document.select("div.user-info > a[href]")
                .forEach(
                        e -> participants.add(e.text())
                );
        this.document.select("a.author")
                .forEach(
                        e -> participants.add(e.text())
                );
        return participants;
    }

    @Override
    public HtmlContent body() {
        return new HtmlContent(
                this.document.select(
                        "div.post-body > div.js-editable > " +
                                "div.js-editable-content"
                ).get(0).html()
        );
    }

    @Override
    public String link() {
        return this.document.location();
    }

    @Override
    public String toString() {
        return "link: " + this.link() + "\n" +
                "title: " + this.title() + "\n" +
                "author: " + this.author() + "\n" +
                "date: " + this.date() + "\n" +
                "tags: " + this.tags() + "\n" +
                "participants: " + this.participants() + "\n" +
                "body: " + this.body() + "\n" +
                "body(html): " + this.body() + "\n";
    }
}
