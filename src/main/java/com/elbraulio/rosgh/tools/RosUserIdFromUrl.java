package com.elbraulio.rosgh.tools;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RosUserIdFromUrl {
    private final String url;

    public RosUserIdFromUrl(String url) {
        this.url = url;
    }

    public int id() {
        return Integer.parseInt(
                this.url.split("/")[4]
        );
    }
}
