package org.elbraulio.rosgh.question;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Answer {


    private final int author;
    private final String type;
    private final String date;
    private final int votes;
    private final boolean isAccepted;

    public Answer(int author, String type, String date, int votes, boolean
            isAccepted) {

        this.author = author;
        this.type = type;
        this.date = date;
        this.votes = votes;
        this.isAccepted = isAccepted;
    }

    public int author() {
        return this.author;
    }

    public String type() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        return author == answer.author;
    }

    @Override
    public int hashCode() {
        return author;
    }

    public String date() {
        return this.date;
    }

    public int votes() {
        return this.votes;
    }

    public boolean isAccepted() {
        return this.isAccepted;
    }
}
