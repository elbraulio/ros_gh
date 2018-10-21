package org.elbraulio.rosgh.tools;

import org.elbraulio.rosgh.github.RosPackage;

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
                    if (isOnGithub("source", repo)) {
                        repoUrl = repo.getJsonObject("source")
                                .getString("url");
                        source = "source";
                    } else if (isOnGithub("doc", repo)) {
                        repoUrl = repo.getJsonObject("doc")
                                .getString("url");
                        source = "doc";
                    } else if (isOnGithub("release", repo)) {
                        repoUrl = repo.getJsonObject("release")
                                .getString("url");
                        source = "release";
                    } else {
                        System.out.println("NOT FOUND ON GITHUB : " + pckg);
                        source = "";
                        repoUrl = "";
                    }

                    rosPackages.add(new RosPackage(pckg, repoUrl, source));
                }
        );

        return rosPackages;
    }

    private boolean isOnGithub(String source, JsonObject repo){
        if(repo.containsKey(source)){
            final String url = repo.getJsonObject(source).getString("url");
            final String[] info = url.split("/");
            return info[2].equals("github.com");
        }
        return false;
    }

    @Override
    public List<RosPackage> take(int length) {
        return asList().subList(0, length);
    }
}
