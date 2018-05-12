package tools;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class LastRosUserPage {

    private final Document document;

    public LastRosUserPage(final Document document){
        this.document = document;
    }

    public int value(){
        final Elements elements = this.document.select(".last-page");
        return Integer.parseInt(elements.get(0).attr("data-page"));
    }
}
