import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import org.junit.Ignore;
import org.junit.Test;
import tools.CanRequest;

import javax.json.*;
import java.io.*;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GithubInfoTest {

    @Ignore
    @Test
    public void test1() throws IOException {
        final String token = "62d4bef9321253abf1a4525f97eb6744bd3b1b24";
        final String path = "src/test/java/resources/github/distribution.json";

        // repositorios
        File jsonInputFile = new File(path);
        InputStream is = new FileInputStream(jsonInputFile);
        JsonReader reader = Json.createReader(is);
        JsonObject empObj = reader.readObject();
        reader.close();
        JsonObject repositories = empObj.getJsonObject("repositories");
        final CanRequest canRequest = new CanRequest(60);
        repositories.forEach(
                (k, v) -> {
                    JsonObject repo = repositories.getJsonObject(k);
                    String url = "";
                    String type = "not found";
                    if (repo.containsKey("source")) {
                        url = repo.getJsonObject("source")
                                .getString("url");
                        type = "source";
                    } else if (repo.containsKey("doc")) {
                        url = repo.getJsonObject("doc")
                                .getString("url");
                        type = "doc";
                    } else if (repo.containsKey("release")) {
                        url = repo.getJsonObject("release")
                                .getString("url");
                        type = "release";
                    }
                    String[] info = url.split("/");
                    String ownerLogin = info[3];
                    String repoName = info[4].substring(
                            0, info[4].length() - 4
                    );
                    String stored = info[2];
                    if (!stored.equals("github.com")) {
                        System.out.println("NOT FOUND ON GITHUB : " + repoName);
                        return;
                    }
                    String repoUrl = ownerLogin + "/" + repoName;

                    // repo info
                    Github github = new RtGithub(token);
                    try {
                        JsonObject repoInfo = github.entry()
                                .uri().path("/repos/" + repoUrl)
                                .back()
                                .fetch()
                                .as(JsonResponse.class).json().readObject();
                        canRequest.waitForRate();

                        String repoFullName = repoInfo.getString("full_name");
                        String description = "";
                        if (!repoInfo.isNull("description"))
                            description = repoInfo.getString("description");

                        System.out.println(
                                "name" + " : " + repoUrl + "\n" +
                                        "description : " + description +
                                        "\ncontributors : "
                        );

                        // contribuidores con login
                        JsonArray contributors = tryJsonArray(
                                3, github, repoUrl, canRequest
                        );

                        for (int i = 0; i < contributors.size(); i++) {
                            JsonObject cntrbtor = contributors.getJsonObject(i);
                            int commits = cntrbtor.getInt("total");
                            String author = cntrbtor.getJsonObject("author")
                                    .getString("login");
                            System.out.println("- " + author + " x " + commits);

                            github.entry()
                                    .uri().path("/users/" + author)
                                    .back()
                                    .method(Request.GET)
                                    .fetch()
                                    .as(JsonResponse.class).json()
                                    .readObject().forEach(
                                    (k3, v3) ->
                                            System.out.println(k3 + ":" + v3)
                            );
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
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
}
