package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Post;

import java.util.List;

/**
 * @author dan
 */
public abstract class PostDao extends GenericJpaDao<Post, Long> {
    public PostDao() {
        setType(Post.class);
    }
    public abstract List<Post> findAllPosts(boolean fetchTags);
}
