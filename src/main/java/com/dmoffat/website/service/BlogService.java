package com.dmoffat.website.service;

import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import com.dmoffat.website.view.pagination.Page;
import com.dmoffat.website.view.pagination.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mega service for everything, since there's barely any functionality right now.
 *
 * There are two variants for findXXX methods:
 * - No PageRequest parameter, always returns the first page
 * - With an additional PageRequest parameter, returns the requested page
 *
 * @author dan
 */
public interface BlogService {
    /** Post administration **/
    void save(Post post);
    Post update(Post post);
    Post update(Post post, String originalContent);
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
    Page<Post> findAllPosts();
    Page<Post> findAllPosts(PageRequest pageRequest);

    Page<Post> findAllPublishedPosts();
    Page<Post> findAllPublishedPosts(PageRequest pageRequest);

    Page<Post> findAllPostsWithTags();
    Page<Post> findAllPostsWithTags(PageRequest pageRequest);

    Page<Post> findAllPostsWithTagsAndComments();
    Page<Post> findAllPostsWithTagsAndComments(PageRequest pageRequest);

    Page<Post> findAllPostsWithTagsAndCommentsAndDiffs();
    Page<Post> findAllPostsWithTagsAndCommentsAndDiffs(PageRequest pageRequest);

    Page<Tag> findAllTags();
    Page<Tag> findAllTags(PageRequest pageRequest);

    Page<Comment> findAllComments();
    Page<Comment> findAllComments(PageRequest pageRequest);

    /** Tag queries **/
    Tag findTagByValue(String value);
    Tag findTagById(Long id);

    /** Post queries **/
    Post findPostById(Long id);

    Page<Post> findPostByAuthor(String authorName);
    Page<Post> findPostByAuthor(String authorName, PageRequest pageRequest);

    Page<Post> findPostByDate(LocalDateTime date);
    Page<Post> findPostByDate(LocalDateTime date, PageRequest pageRequest);

    Page<Post> findPostBetween(LocalDateTime start, LocalDateTime end);
    Page<Post> findPostBetween(LocalDateTime start, LocalDateTime end, PageRequest pageRequest);

    Page<Post> findRecentPosts();

    boolean archive(Long postId);
}
