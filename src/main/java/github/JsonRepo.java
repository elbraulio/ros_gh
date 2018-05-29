package github;


import com.jcabi.github.Github;
import tools.NullableJsonString;

import javax.json.JsonObject;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class JsonRepo {
    private final JsonObject jsonRepo;

    public JsonRepo(final JsonObject jsonRepo) {

        this.jsonRepo = jsonRepo;
    }

    public String name() {
        return this.jsonRepo.getString("name");
    }

    public String fullName() {
        return this.jsonRepo.getString("full_name");
    }

    public String description() {
        return new NullableJsonString(
                this.jsonRepo, "description"
        ).string();
    }

    public String owner() {
        return this.jsonRepo.getJsonObject("owner").getString("login");
    }
}
