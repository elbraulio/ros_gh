package github;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class JsonRepoTest {

    @Test
    public void oneToOneRelationship() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "ros_gh")
                .add("full_name", "github.com/ros_gh")
                .add("description", "recomendation system")
                .add(
                        "owner",
                        Json.createObjectBuilder()
                                .add("login", "elbraulio")
                ).build();
        GhRepo ghRepo = new JsonRepo(jsonObject);
        assertThat(ghRepo.name(), is("ros_gh"));
        assertThat(ghRepo.fullName(), is("github.com/ros_gh"));
        assertThat(ghRepo.description(), is("recomendation system"));
        assertThat(ghRepo.owner(), is("elbraulio"));
    }
}