package com.dmoffat.website.service;

import com.dmoffat.website.model.Comment;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * @author dan
 */
public interface CommentService {

    // Given a string, will remove all unwanted characters.
    default String clean(String comment) {
        return Jsoup.clean(comment, Whitelist.simpleText());
    };

    // Given a comment, returns a copy with all fields "cleaned".
    default Comment clean(Comment comment) {
        return new Comment.Builder(comment)
                            .content(clean(comment.getContent()))
                            .name(clean(comment.getName()))
                            .build();
    }

    // Transform a comment into some markup language
    String render(String comment);
}
