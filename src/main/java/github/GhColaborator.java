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

    @Override
    public boolean equals(Object o){
        if(o instanceof GhColaborator){
            return ((GhColaborator) o).author.equals(this.author);
        } else {
            return false;
        }
    }
}
