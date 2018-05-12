package tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ParseRosTagCount {
    private final String text;

    public ParseRosTagCount(String text) {
        this.text = text;
    }

    public int count() {
        return Integer.parseInt(this.text.split(" ")[1]);
    }
}
