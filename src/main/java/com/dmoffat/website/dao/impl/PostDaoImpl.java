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

        // Always eagerly fetch the author
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

    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        Root<Post> root = criteriaQuery.from(Post.class);

        root.fetch("author");

        ParameterExpression<String> expression = criteriaBuilder.parameter(String.class, "authorName");
        criteriaQuery.where(criteriaBuilder.equal(root.get("author").get("name"), expression));

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter("authorName", authorName);

        return query.getResultList();
    }

    @Override
    public List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags) {
        return findAllPostsByDateBetween(date, date, fetchTags);
    }

    @Override
    public List<Post> findAllPostsByDateBetween(LocalDateTime start, LocalDateTime end, boolean fetchTags) {
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root).distinct(true);
        root.fetch("author");

        ParameterExpression<LocalDateTime> startDate = criteriaBuilder.parameter(LocalDateTime.class, "startDate");
        ParameterExpression<LocalDateTime> endDate = criteriaBuilder.parameter(LocalDateTime.class, "endDate");
        criteriaQuery.where(criteriaBuilder.between(root.get("created"), startDate, endDate));

        if(fetchTags) {
            root.fetch("tags", JoinType.LEFT);
        }

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter("startDate", start);
        query.setParameter("endDate", end);

        return query.getResultList();
    }

    @Override
    public List<Post> findAllPostsWithTags(int start, int rows) {
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root).distinct(true);
        root.fetch("author");
        root.join("tags", JoinType.LEFT);

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query
            .setFirstResult(start)
            .setMaxResults(rows);

        return query.getResultList();
    }
}
