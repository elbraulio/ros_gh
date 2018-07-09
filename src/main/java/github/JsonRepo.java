package github;


import tools.NullableJsonString;

import javax.json.JsonObject;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class JsonRepo implements GhRepo {
    private final JsonObject jsonRepo;

    public JsonRepo(final JsonObject jsonRepo) {

        this.jsonRepo = jsonRepo;
    }

    @Override
    public String name() {
        return this.jsonRepo.getString("name");
    }

    @Override
    public String fullName() {
        return this.jsonRepo.getString("full_name");
    }

    @Override
    public String description() {
        return new NullableJsonString(
                this.jsonRepo, "description"
        ).string();
    }

    @Override
    public String owner() {
        if(jsonRepo.getJsonObject("owner") != null) {
            return this.jsonRepo.getJsonObject("owner").getString("login");
        } else {
            return "";
        }
    }

    @Override
    public int dbId() {
        return -1;
    }
}
