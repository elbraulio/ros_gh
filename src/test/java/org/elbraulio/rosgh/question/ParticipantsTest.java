package org.elbraulio.rosgh.question;

import org.jsoup.Jsoup;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class ParticipantsTest {

    @Test
    @Ignore
    public void answers() throws IOException {
        List<Answer> answers = new Participants(
                Jsoup.connect("https://answers.ros.org/question/11887").get()
        ).asList();
        for (Answer answer : answers) {
            System.out.println("-----------");
            System.out.println(answer.author());
            System.out.println(answer.type());
            System.out.println(answer.date());
            System.out.println(answer.votes());
            System.out.println(answer.isAccepted());
        }
    }
}