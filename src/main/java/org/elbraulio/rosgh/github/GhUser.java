package org.elbraulio.rosgh.github;

import org.elbraulio.rosgh.tools.NullableJsonString;

import javax.json.JsonObject;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUser {
    private final int id;
    private final String login;
    private final int followers;
    private final String url;
    private final String name;
    private final String company;
    private final String email;

    public GhUser(JsonObject jsonObject) {
        this(
                0,
                new NullableJsonString(jsonObject, "login").string(),
                jsonObject.getInt("followers"),
                jsonObject.getString("html_url"),
                new NullableJsonString(jsonObject,"name").string(),
                new NullableJsonString(jsonObject, "company").string(),
                new NullableJsonString(jsonObject, "email").string()
        );
    }

    public GhUser(
            int id, String login, int followers, String url, String name,
            String company, String email
    ) {

        this.id = id;
        this.login = login;
        this.followers = followers;
        this.url = url;
        this.name = name;
        this.company = company;
        this.email = email;
    }

    public int dbId() {
        return this.id;
    }

    public String login() {
        return this.login;
    }

    public int followers() {
        return this.followers;
    }

    public String url() {
        return this.url;
    }

    public String name() {
        return this.name;
    }

    public String company() {
        return this.company;
    }

    public String email() {
        return this.email;
    }
}
