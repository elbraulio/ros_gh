package github;


import com.jcabi.github.Github;
import com.jcabi.http.response.JsonResponse;
import tools.ExtractRepoFromShortUrl;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class RosPackage {
    private final String name;
    private final String repoUrl;
    private final String source;

    public RosPackage(String name, String repoUrl, String source) {

        this.name = name;
        this.repoUrl = repoUrl;
        this.source = source;
    }

    public GhRepo asRepo(Github github) throws IOException {
        return new JsonRepo(
                github.entry()
                        .uri()
                        .path(
                                "/repos/" + new ExtractRepoFromShortUrl(this.repoUrl)
                                        .repoFullName()
                        )
                        .back()
                        .fetch()
                        .as(JsonResponse.class).json().readObject()
        );
    }

    public String source() {
        return this.source;
    }

    public String name() {
        return this.name;
    }
}
