package com.dmoffat.website.service;

import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mega service for everything, since there's barely any functionality right now.
 *
 * @author dan
 */
public interface BlogService {
    /** Post administration **/
    void save(Post post);
    Post update(Post post);
    void publish(Post post);
    void hide(Post post);

    /** Tag administration **/
    void addTagTo(Post post, Tag tag);
    void addTagsTo(Post post, List<Tag> tags);
    void removeTagFrom(Post post, Tag tag);
    void removeTagsFrom(Post post, List<Tag> tags);

    /** Comment administration **/
    void addCommentTo(Post post, Comment comment);
    void removeCommentFrom(Post post, Comment comment);
    void remove(Comment comment);

    /** Find all queries **/
    List<Post> findAllPosts();
    List<Tag> findAllTags();
    List<Comment> findAllComments();

    /** Tag queries **/
    Tag findTagByValue(String value);
    Tag findTagById(Long id);

    /** Post queries **/
    Post findPostById(Long id);
    List<Post> findPostByAuthor(String authorName);
    List<Post> findPostByDate(LocalDateTime date);
    List<Post> findPostBetween(LocalDateTime start, LocalDateTime end);
}
