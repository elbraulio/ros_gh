package tools;

import github.RosPackage;

import javax.json.JsonObject;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class PackagesFromJson implements CanBeList<RosPackage> {
    private final JsonObject packagesAsJson;

    public PackagesFromJson(JsonObject packagesAsJson) {
        this.packagesAsJson = packagesAsJson;
    }

    @Override
    public List<RosPackage> asList() {
        final List<RosPackage> rosPackages = new LinkedList<>();
        final JsonObject repositories =
                this.packagesAsJson.getJsonObject("repositories");
        repositories.forEach(
                (pckg, v) -> {
                    final JsonObject repo =
                            repositories.getJsonObject(pckg);
                    String repoUrl = "";
                    String source = "";
                    if (repo.containsKey("source")) {
                        repoUrl = repo.getJsonObject("source")
                                .getString("url");
                        source = "source";
                    } else if (repo.containsKey("doc")) {
                        repoUrl = repo.getJsonObject("doc")
                                .getString("url");
                        source = "doc";
                    } else if (repo.containsKey("release")) {
                        repoUrl = repo.getJsonObject("release")
                                .getString("url");
                        source = "release";
                    }
                    if(!repoUrl.isEmpty()){
                        String[] info = repoUrl.split("/");
                        String stored = info[2];
                        if (!stored.equals("github.com")) {
                            source = "";
                            repoUrl = "";
                            System.out.println("NOT FOUND ON GITHUB : " + pckg);
                        }
                    }

                    rosPackages.add(new RosPackage(pckg, repoUrl, source));
                }
        );

        return rosPackages;
    }

    @Override
    public List<RosPackage> take(int length) {
        return asList().subList(0, length);
    }
}
