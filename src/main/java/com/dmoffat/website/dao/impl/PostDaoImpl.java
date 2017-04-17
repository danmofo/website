package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author dan
 */
@Repository("postDao")
public class PostDaoImpl extends PostDao {
    @Override
    public List<Post> findAllPosts(boolean fetchTags) {
        if(!fetchTags) {
            return findAll();
        }

        CriteriaQuery<Post> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root);
        root.fetch("tags");

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }
}
