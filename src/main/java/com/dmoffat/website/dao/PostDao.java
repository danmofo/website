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
    public abstract List<Post> findAllPosts(boolean fetchTags);
    public abstract List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags);
    public abstract List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags);
    public abstract List<Post> findAllPostsByDateBetween(LocalDateTime start, LocalDateTime end, boolean fetchTags);
}
