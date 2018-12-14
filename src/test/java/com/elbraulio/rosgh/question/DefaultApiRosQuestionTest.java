package com.elbraulio.rosgh.question;

import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class DefaultApiRosQuestionTest {

    @Test
    public void basicJsonResponse() throws FileNotFoundException {
        final String jsonPath = "" +
                "src/test/resources/basicQuestionApiResponse" +
                ".json";
        final JsonObject json = Json.createReader(
                new FileInputStream(new File(jsonPath))
        ).readObject().getJsonArray("questions").getJsonObject(0);
        final ApiRosQuestion question = new DefaultApiRosQuestion(json);

        assertEquals(9033, question.id());

        final List<String> tags = new ArrayList<>();
        tags.add("hardware");
        tags.add("drivers");

        assertEquals(tags, question.tags());

        assertEquals(2, question.answerCount());

        assertEquals(13036, question.acceptedAnswerId());

        final List<Integer> answerIds = new ArrayList<>();
        answerIds.add(13031);
        answerIds.add(13036);

        assertEquals(answerIds, question.answerIds());

        assertEquals(3, question.lastActivityBy());

        assertEquals(677, question.viewCount());

        assertEquals("1297774294", question.lastActivityAt());

        assertEquals(
                "what hardware drivers does ROS provide?",
                question.title()
        );

        assertEquals(
                "http://answers.ros.org/question/9033/what-hardware-d" +
                        "rivers-does-ros-provide/",
                question.url()
        );

        assertEquals(2, question.author());

        assertEquals("1297650523", question.addedAt());

        assertEquals(
                "<p>A list of stacks, packages, or tutorials related to " +
                        "hardware drivers.</p>\n",
                question.summary()
        );
    }
}