package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.view.pagination.Sort;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Most methods has three variants:
 * - No start / rows, returns the first 10 results
 * - With start / rows returns the specified page of results.
 * - Count of results
 *
 * The first variant just calls the second variant with default values (start = 0, rows = 10)
 *
 * todo: write a generic JPA query that can count based on an arbitrary predicate / query
 * todo: possibly use the PageRequest directly to avoid all of the similar methods
 *
 * @author dan
 */
public abstract class PostDao extends GenericJpaDao<Post, Long> {
    public PostDao() {
        setType(Post.class);
    }

    public abstract List<Post> findAllPublishedPosts();
    public abstract List<Post> findAllPublishedPosts(int start, int rows);
    public abstract List<Post> findAllPublishedPosts(int start, int rows, Sort sort);

    public abstract List<Post> findAllUnpublishedPosts();
    public abstract List<Post> findAllUnpublishedPosts(int start, int rows);
    public abstract List<Post> findAllUnpublishedPosts(int start, int rows, Sort sort);

    public abstract Long countPublishedPosts();
    public abstract Long countUnpublishedPosts();

    public abstract List<Post> findAllPostsWithTags();
    public abstract List<Post> findAllPostsWithTags(int start, int rows);
    public abstract List<Post> findAllPostsWithTags(int start, int rows, Sort sort);

    // todo: add sort
    public abstract List<Post> findAllPostsWithTagsAndComments();
    public abstract List<Post> findAllPostsWithTagsAndComments(int start, int rows);

    // todo: add sort
    public abstract List<Post> findAllPostsWithTagsAndCommentsAndDiffs();
    public abstract List<Post> findAllPostsWithTagsAndCommentsAndDiffs(int start, int rows);

    public abstract List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags);
    public abstract List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags, int start, int rows);
    public abstract List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags, int start, int rows, Sort sort);
    public abstract Long countPostsByAuthor(String authorName);

    // todo: add sort
    public abstract List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags);
    public abstract List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags, int start, int rows);
    public abstract Long countPostsByDate(LocalDateTime date);

    // todo: add sort
    public abstract List<Post> findAllPostsByDateBetween(LocalDateTime startDate, LocalDateTime endDate, boolean fetchTags);
    public abstract List<Post> findAllPostsByDateBetween(LocalDateTime startDate, LocalDateTime endDate, boolean fetchTags, int start, int rows);
    public abstract Long countPostsByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}