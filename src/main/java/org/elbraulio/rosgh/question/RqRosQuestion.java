package org.elbraulio.rosgh.question;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

import javax.json.JsonArray;
import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class RqRosQuestion {
    private final String url =
            "https://answers.ros.org/api/v1/questions/?sort=age-asc&page=";


    private final int page;

    public RqRosQuestion(int page) {
        this.page = page;
    }

    public JsonArray jsonArray() throws IOException {
        return new JdkRequest(this.url + this.page)
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readObject()
                .getJsonArray("questions");
    }
}
