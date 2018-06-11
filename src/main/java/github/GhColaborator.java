package github;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhColaborator {
    private final String author;
    private final int total;

    public GhColaborator(String author, int total) {

        this.author = author;
        this.total = total;
    }

    public String login() {
        return this.author;
    }

    public int commits() {
        return this.total;
    }
}
