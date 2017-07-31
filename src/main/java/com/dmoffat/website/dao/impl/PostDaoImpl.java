package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
    public List<Post> findAllPostsWithTags() {
        return findAllPosts(true, false, false);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndComments() {
        return findAllPosts(true, true, false);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndCommentsAndDiffs() {
        return findAllPosts(true, true, true);
    }

    private List<Post> findAllPosts(boolean fetchTags, boolean fetchComments, boolean fetchDiffs) {
        CriteriaQuery<Post> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root).distinct(true);

        // Always fetch the author
        root.fetch("author", JoinType.INNER);

        if(fetchTags) {
            root.fetch("tags", JoinType.LEFT);
        }

        if(fetchComments) {
            root.fetch("comments", JoinType.LEFT);
        }

        if(fetchDiffs) {
            root.fetch("diffs", JoinType.LEFT);
        }

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);

        return query.getResultList();
    }

    // todo: use typed queries to allow conditional retrieval of related entities
    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        Root<Post> root = criteriaQuery.from(Post.class);

        root.join("author");

        ParameterExpression<String> expression = criteriaBuilder.parameter(String.class, "authorName");
        criteriaQuery.where(criteriaBuilder.equal(root.get("author").get("name"), expression));

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
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
