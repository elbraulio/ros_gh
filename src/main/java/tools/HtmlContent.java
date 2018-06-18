package tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class HtmlContent {
    private final String html;

    public HtmlContent(String html) {

        this.html = html;
    }

    @Override
    public String toString(){
        return this.html;
    }
}
