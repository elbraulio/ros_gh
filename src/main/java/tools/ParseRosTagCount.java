package tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class ParseRosTagCount {
    private final String text;

    public ParseRosTagCount(final String text) {
        this.text = text;
    }

    public int count() {
        return Integer.parseInt(this.text.split(" ")[1]);
    }
}
