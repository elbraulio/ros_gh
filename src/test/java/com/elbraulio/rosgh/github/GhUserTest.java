package com.elbraulio.rosgh.github;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class GhUserTest {

    @Test
    public void oneToOneRelationship() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("login", "elbraulio")
                .add("followers", 33)
                .add("html_url", "github.com/elbraulio")
                .add("name", "braulio")
                .add("company", "dcc")
                .add("email", "fake@mail.com")
                .build();
        GhUser ghUser = new GhUser(jsonObject);
        assertThat(
                ghUser.login(), is("elbraulio")
        );
        assertThat(
                ghUser.followers(), is(33)
        );
        assertThat(
                ghUser.url(), is("github.com/elbraulio")
        );
        assertThat(
                ghUser.name(), is("braulio")
        );
        assertThat(
                ghUser.company(), is("dcc")
        );
        assertThat(
                ghUser.email(), is("fake@mail.com")
        );
    }
}