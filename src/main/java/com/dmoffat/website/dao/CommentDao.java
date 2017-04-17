package com.dmoffat.website.dao;

import com.dmoffat.web.common.dao.GenericJpaDao;
import com.dmoffat.website.model.Comment;

/**
 * @author dan
 */
public abstract class CommentDao extends GenericJpaDao<Comment, Long> {
    public CommentDao() {
        setType(Comment.class);
    }
}
