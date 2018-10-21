package org.elbraulio.rosgh.question;

import org.elbraulio.rosgh.tools.IntFromJsonArray;
import org.elbraulio.rosgh.tools.StringsFromJsonArray;

import javax.json.JsonObject;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultApiRosQuestion implements ApiRosQuestion {


    private final JsonObject json;

    public DefaultApiRosQuestion(JsonObject json) {

        this.json = json;
    }

    @Override
    public List<String> tags() {
        return new StringsFromJsonArray(
                this.json.getJsonArray("tags")
        ).list();
    }

    @Override
    public int answerCount() {
        return this.json.getInt("answer_count");
    }

    @Override
    public int acceptedAnswerId() {
        return this.json.getInt("accepted_answer_id");
    }

    @Override
    public List<Integer> answerIds() {
        return new IntFromJsonArray(
                this.json.getJsonArray("answer_ids")
        ).list();
    }

    @Override
    public int id() {
        return this.json.getInt("id");
    }

    @Override
    public int lastActivityBy() {
        return this.json.getJsonObject("last_activity_by").getInt("id");
    }

    @Override
    public int viewCount() {
        return this.json.getInt("view_count");
    }

    @Override
    public String lastActivityAt() {
        return this.json.getString("last_activity_at");
    }

    @Override
    public String title() {
        return this.json.getString("title");
    }

    @Override
    public String url() {
        return this.json.getString("url");
    }

    @Override
    public int author() {
        return this.json.getJsonObject("author").getInt("id");
    }

    @Override
    public String addedAt() {
        return this.json.getString("added_at");
    }

    @Override
    public String summary() {
        return this.json.getString("summary");
    }
}
