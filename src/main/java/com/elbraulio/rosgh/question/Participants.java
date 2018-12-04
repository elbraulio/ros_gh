package com.elbraulio.rosgh.question;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Braulio Lopez (brauliop.3@gmail.com)
 */
public class Participants {
    private final Document document;

    public Participants(Document document) {

        this.document = document;
    }

    public List<Answer> asList() {
        List<Answer> answers = new LinkedList<>();
        // answers
        this.document.select(
                "div.answer"
        ).forEach(
                e -> {
                    answers.add(
                            new Answer(
                                    Integer.parseInt(
                                            e.selectFirst(
                                                    "div.user-card > " +
                                                            "a[href]"
                                            ).attr("href")
                                                    .split("/")[2]
                                    ),
                                    "answer",
                                    e.selectFirst("abbr.timeago").attr("title"),
                                    Integer.parseInt(
                                            e.selectFirst("div.vote-number")
                                                    .text()
                                    ),
                                    e.hasClass("accepted-answer")
                            )
                    );
                }
        );

        // comments
        this.document.select(
                "div.comment"
        ).forEach(
                e -> {
                    final Element href = e.selectFirst("a.js-avatar-box");
                    if (!href.attr("href").isEmpty()) {
                        answers.add(
                                new Answer(
                                        Integer.parseInt(
                                                href.attr("href")
                                                        .split("/")[2]
                                        ),
                                        "comment",
                                        e.selectFirst("abbr.timeago").attr("title"),
                                        new CommentVoteCount(
                                                e.selectFirst(
                                                        "div.comment-votes > " +
                                                                "div.upvote"
                                                ).text()
                                        ).count(),
                                        e.hasClass("accepted-answer"))
                        );
                    }
                }
        );

        return answers;
    }
}
