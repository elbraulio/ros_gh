package tools;

import github.RosPackage;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class FromJsonFile {
    private final String path;

    public FromJsonFile(final String path) {

        this.path = path;
    }

    public List<RosPackage> repoList() throws FileNotFoundException {
        JsonReader reader = Json.createReader(
                new FileInputStream(new File(path))
        );
        JsonObject packagesAsJson = reader.readObject();
        reader.close();
        return new PackagesFromJson(packagesAsJson).asList();
    }
}
