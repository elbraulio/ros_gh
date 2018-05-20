import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import org.junit.Test;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GithubInfoTest {

    public class CanRequest {

        private List<Long> time;

        public CanRequest() {
            this.time = new ArrayList<>();
        }

        public void waitForRate() throws InterruptedException {
            int count = 0;
            long currentTime = System.currentTimeMillis();
            time.add(currentTime);
            int first = 0;
            for (int i = time.size() - 1; i >= 0; i--) {
                long lapsed = currentTime - time.get(i);
                if (lapsed < 60000L) {
                    count++;
                    first = i;
                } else {
                    break;
                }
            }

            if (count >= 30) {
                long sleep = 60000 + 5000 - (
                        currentTime - time.get(first)
                );
                System.out.println(
                        "waiting " + sleep + " milliseconds for rate"
                );
                Thread.sleep(sleep);
            }

        }

        public int total() {
            return this.time.size();
        }
    }

    @Test
    public void test1() throws IOException {
        final String token = "5b015cacfd843a3df2552133ea612f096fdccff2";
        final String path = "src/test/java/resources/github/distribution.json";

        // repositorios
        File jsonInputFile = new File(path);
        InputStream is = new FileInputStream(jsonInputFile);
        JsonReader reader = Json.createReader(is);
        JsonObject empObj = reader.readObject();
        reader.close();
        JsonObject repositories = empObj.getJsonObject("repositories");
        final CanRequest canRequest = new CanRequest();
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

                        String fullName = repoInfo.getString("full_name");
                        String description = "";
                        if (!repoInfo.isNull("description"))
                            description = repoInfo.getString("description");

                        System.out.println(
                                "name" + " : " + repoName + "\n" +
                                        "description : " + description +
                                        "\ncontributors : "
                        );

                        /* TODO: 20-05-18 evitar object en array
                        try, si es un objecto, intento otra vez y cada
                        intento llama a canRequest.
                         */
                        
                        // contribuidores con login
                        JsonArray contributors = github.entry()
                                .uri().path("/repos/" + repoUrl
                                        + "/stats/contributors")
                                .back()
                                .fetch()
                                .as(JsonResponse.class).json().readArray();
                        canRequest.waitForRate();

                        for (int i = 0; i < contributors.size(); i++) {
                            JsonObject cntrbtor = contributors.getJsonObject(i);
                            int commits = cntrbtor.getInt("total");
                            String author = cntrbtor.getJsonObject("author")
                                    .getString("login");
                            System.out.println("- " + author + " x " + commits);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println(canRequest.total());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(canRequest.total());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(canRequest.total());
                    }
                }
        );




/*


        // user info
        Map<String, String> s = new HashMap<>();
        github.entry()
                .uri().path("/users/elbraulio")
                .back()
                .method(Request.GET)
                .fetch()
                .as(JsonResponse.class).json().readObject().forEach(
                (k, v) -> System.out.println(k + ":" + v)
        );

*/
    }
}
