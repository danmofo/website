package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

/**
 * todo: rewrite HQL statements
 *
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

    // todo: use typed queries to allow conditional retrieval of related entities
    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags) {
        TypedQuery<Post> query = getEntityManager().createQuery("from Post p inner join fetch p.tags where p.author = :authorName", Post.class);
        query.setParameter("authorName", authorName);

        return query.getResultList();
    }

    @Override
    public List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags) {
        TypedQuery<Post> query = getEntityManager().createQuery("from Post p inner join fetch p.tags where p.posted = :date", Post.class);
        query.setParameter("date", date);

        return query.getResultList();
    }

    @Override
    public List<Post> findAllPostsByDateBetween(LocalDateTime start, LocalDateTime end, boolean fetchTags) {
        TypedQuery<Post> query = getEntityManager().createQuery("from Post p inner join fetch p.tags where p.posted between :start and :end", Post.class);
        query.setParameter("start", start);
        query.setParameter("end", end);

        return query.getResultList();
    }
}
