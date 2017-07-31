package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Post;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dan
 */
public abstract class PostDao extends GenericJpaDao<Post, Long> {
    public PostDao() {
        setType(Post.class);
    }
    public abstract List<Post> findAllPostsWithTags();
    public abstract List<Post> findAllPostsWithTagsAndComments();
    public abstract List<Post> findAllPostsWithTagsAndCommentsAndDiffs();
    public abstract List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags);
    public abstract List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags);
    public abstract List<Post> findAllPostsByDateBetween(LocalDateTime start, LocalDateTime end, boolean fetchTags);

    public abstract List<Post> findAllPostsWithTags(int start, int rows);
}
