package org.elbraulio.rosgh.github;


import org.elbraulio.rosgh.tools.NullableJsonString;

import javax.json.JsonObject;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public final class JsonRepo implements GhRepo {
    private final JsonObject jsonObject;

    public JsonRepo(final JsonObject jsonObject) {

        this.jsonObject = jsonObject;
    }

    @Override
    public String name() {
        return this.jsonObject.getString("name");
    }

    @Override
    public String fullName() {
        return this.jsonObject.getString("full_name");
    }

    @Override
    public String description() {
        return new NullableJsonString(
                this.jsonObject, "description"
        ).string();
    }

    @Override
    public String owner() {
        if(jsonObject.getJsonObject("owner") != null) {
            return this.jsonObject.getJsonObject("owner").getString("login");
        } else {
            return "";
        }
    }

    @Override
    public int dbId() {
        return -1;
    }
}
