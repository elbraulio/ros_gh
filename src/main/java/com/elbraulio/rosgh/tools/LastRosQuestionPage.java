package com.elbraulio.rosgh.tools;

import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;

import java.io.IOException;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class LastRosQuestionPage {

    private final String url =
            "https://answers.ros.org/api/v1/questions/?sort=age-asc";

    public int value() throws IOException {
        return new JdkRequest(this.url)
                .fetch()
                .as(JsonResponse.class)
                .json()
                .readObject()
                .getInt("pages");
    }
}
