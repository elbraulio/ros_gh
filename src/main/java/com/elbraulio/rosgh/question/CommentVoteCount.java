package com.elbraulio.rosgh.question;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class CommentVoteCount {
    private final String number;

    public CommentVoteCount(String number) {
        this.number = number;
    }

    public int count() {
        return number.isEmpty() ? 0 : Integer.parseInt(number);
    }
}
