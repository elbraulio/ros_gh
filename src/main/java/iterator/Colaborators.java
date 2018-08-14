package iterator;

import com.jcabi.github.Github;
import com.jcabi.http.response.JsonResponse;
import github.GhColaborator;
import tools.CanRequest;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Colaborators {

    private final String fullName;
    private final CanRequest canRequest;
    private final Github github;

    public Colaborators(String fullName, CanRequest canRequest, Github github) {

        this.fullName = fullName;
        this.canRequest = canRequest;
        this.github = github;
    }

    private JsonArray tryJsonArray(
            int tries, Github github, String repoUrl, CanRequest canRequest
    ) throws IOException, InterruptedException {
        try {
            JsonArray array = github.entry()
                    .uri().path("/repos/" + repoUrl
                            + "/stats/contributors")
                    .back()
                    .fetch()
                    .as(JsonResponse.class).json().readArray();
            canRequest.waitForRate();
            return array;
        } catch (JsonException je) {
            if (tries > 0) {
                return tryJsonArray(
                        tries - 1, github, repoUrl, canRequest
                );
            } else {
                throw je;
            }
        }
    }

    public List<GhColaborator> colaboratorList()
            throws IOException, InterruptedException {
        final List<GhColaborator> list = new LinkedList<>();
        final JsonArray contributors = tryJsonArray(
                100, this.github, this.fullName, this.canRequest
        );

        for (int i = 0; i < contributors.size(); i++) {
            final JsonObject cntrbtor = contributors.getJsonObject(i);
            list.add(
                new GhColaborator(
                        cntrbtor.getJsonObject("author").getString("login"),
                        cntrbtor.getInt("total")
                )
            );
        }
        return list;
    }
}
