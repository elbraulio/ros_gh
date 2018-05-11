package tools;


import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class TotalRosUserPages {

    private final Document document;

    public TotalRosUserPages(final Document document){
        this.document = document;
    }

    public int totalPages(){
        final Elements elements = document.select(".last-page");
        return Integer.parseInt(elements.get(0).attr("data-page"));
    }
}
