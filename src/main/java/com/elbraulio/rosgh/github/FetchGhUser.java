package com.elbraulio.rosgh.github;

import com.jcabi.github.Github;
import com.jcabi.http.response.JsonResponse;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class FetchGhUser {
    private final Github github;
    private final String owner;

    public FetchGhUser(Github github, String owner) {

        this.github = github;
        this.owner = owner;
    }

    public GhUser ghUser() throws IOException {
        return new GhUser(
                this.github.entry()
                .uri().path("/users/" + this.owner)
                .back()
                .fetch()
                .as(JsonResponse.class).json().readObject()
        );
    }
}
