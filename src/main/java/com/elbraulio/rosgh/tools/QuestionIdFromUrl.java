package com.elbraulio.rosgh.tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class QuestionIdFromUrl {
    private final String location;

    public QuestionIdFromUrl(String location) {
        this.location = location;
    }

    public String id() {
        return this.location.split("/")[4];
    }
}
