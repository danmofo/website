package com.dmoffat.website.dao.impl;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.view.pagination.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author dan
 */
@Repository("postDao")
public class PostDaoImpl extends PostDao {
    private static final int DEFAULT_START = 0;
    private static final int DEFAULT_ROWS = 10;
    private static final Sort DEFAULT_SORT = Sort.idAsc();

    @Override
    public List<Post> findAllPublishedPosts() {
        return findAllPublishedPosts(DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPublishedPosts(int start, int rows) {
        return findAllPostsByPublished(true, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllPublishedPosts(int start, int rows, Sort sort) {
        return findAllPostsByPublished(true, start, rows, sort);
    }

    @Override
    public Long countPublishedPosts() {
        return countAllByPublished(true);
    }

    @Override
    public List<Post> findAllUnpublishedPosts() {
        return findAllPostsByPublished(false, DEFAULT_START, DEFAULT_ROWS, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllUnpublishedPosts(int start, int rows) {
        return findAllPostsByPublished(false, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllUnpublishedPosts(int start, int rows, Sort sort) {
        return findAllPostsByPublished(false, start, rows, sort);
    }

    @Override
    public Long countUnpublishedPosts() {
        return countAllByPublished(false);
    }

    @Override
    public List<Post> findAllPostsWithTags() {
        return findAllPostsWithTags(DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsWithTags(int start, int rows) {
        return findAllPosts(true, false, false, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllPostsWithTags(int start, int rows, Sort sort) {
        return findAllPosts(true, false, false, start, rows, sort);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndComments() {
        return findAllPostsWithTagsAndComments(DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndComments(int start, int rows) {
        return findAllPosts(true, true, false, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndCommentsAndDiffs(int start, int rows) {
        return findAllPosts(true, true, true, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllPostsWithTagsAndCommentsAndDiffs() {
        return findAllPostsWithTags(DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags) {
        return findAllPostsByAuthor(authorName, fetchTags, DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags, int start, int rows) {
        return findAllPostsByAuthor(authorName, fetchTags, start, rows, DEFAULT_SORT);
    }

    @Override
    public List<Post> findAllPostsByAuthor(String authorName, boolean fetchTags, int start, int rows, Sort sort) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        Root<Post> root = criteriaQuery.from(Post.class);

        root.fetch("author");

        ParameterExpression<String> expression = criteriaBuilder.parameter(String.class, "authorName");
        criteriaQuery.where(criteriaBuilder.equal(root.get("author").get("name"), expression));
        criteriaQuery.orderBy(sortToOrder(sort, criteriaBuilder, root));

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter("authorName", authorName);
        query.setFirstResult(start);
        query.setMaxResults(rows);

        return query.getResultList();
    }

    @Override
    public Long countPostsByAuthor(String authorName) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Post> root = q.from(Post.class);
        root.join("author");

        ParameterExpression<String> expression = cb.parameter(String.class, "authorName");

        Path<String> name = root.get("author").get("name");
        Predicate predicate = cb.equal(name, expression);
        q.select(cb.count(root));
        q.where(predicate);

        TypedQuery<Long> query = getEntityManager().createQuery(q);
        query.setParameter("authorName", authorName);
        return query.getSingleResult();
    }

    @Override
    public Long countPostsByDate(LocalDateTime date) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Post> root = q.from(Post.class);

        ParameterExpression<LocalDateTime> expression = cb.parameter(LocalDateTime.class, "date");
        Path<LocalDateTime> created = root.get("created");

        q.select(cb.count(root));
        q.where(cb.equal(created, expression));

        TypedQuery<Long> query = getEntityManager().createQuery(q);
        query.setParameter("date", date);
        return query.getSingleResult();
    }

    @Override
    public Long countPostsByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        CriteriaBuilder cb = criteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Post> root = q.from(Post.class);
        Path<LocalDateTime> created = root.get("created");

        ParameterExpression<LocalDateTime> start = cb.parameter(LocalDateTime.class, "start");
        ParameterExpression<LocalDateTime> end = cb.parameter(LocalDateTime.class, "end");

        q.select(cb.count(root));
        q.where(cb.between(created, start, end));

        TypedQuery<Long> query = getEntityManager().createQuery(q);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);

        return query.getSingleResult();
    }

    @Override
    public List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags) {
        return findAllPostsByDate(date, fetchTags, DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsByDate(LocalDateTime date, boolean fetchTags, int start, int rows) {
        return findAllPostsByDateBetween(date, date, fetchTags);
    }

    @Override
    public List<Post> findAllPostsByDateBetween(LocalDateTime start, LocalDateTime end, boolean fetchTags) {
        return findAllPostsByDateBetween(start, end, fetchTags, DEFAULT_START, DEFAULT_ROWS);
    }

    @Override
    public List<Post> findAllPostsByDateBetween(LocalDateTime startDate, LocalDateTime endDate, boolean fetchTags, int start, int rows) {
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root).distinct(true);
        root.fetch("author");

        ParameterExpression<LocalDateTime> s = criteriaBuilder.parameter(LocalDateTime.class, "startDate");
        ParameterExpression<LocalDateTime> e = criteriaBuilder.parameter(LocalDateTime.class, "endDate");
        criteriaQuery.where(criteriaBuilder.between(root.get("created"), s, e));

        if(fetchTags) {
            root.fetch("tags", JoinType.LEFT);
        }

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setFirstResult(start);
        query.setMaxResults(rows);

        return query.getResultList();
    }

    // Internal methods that drive the above methods

    private List<Post> findAllPosts(boolean fetchTags, boolean fetchComments, boolean fetchDiffs, int start, int rows, Sort sort) {
        CriteriaQuery<Post> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);
        criteriaQuery.select(root).distinct(true);
        criteriaQuery.orderBy(sortToOrder(sort, criteriaBuilder(), root));

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
        query.setFirstResult(start);
        query.setMaxResults(rows);

        return query.getResultList();
    }


    private List<Post> findAllPostsByPublished(boolean isPublished, int start, int rows, Sort sort) {
        CriteriaBuilder criteriaBuilder = criteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = criteriaQuery();
        Root<Post> root = criteriaQuery.from(Post.class);
        root.fetch("author");

        criteriaQuery.where(criteriaBuilder.equal(root.get("published"), Boolean.valueOf(isPublished)));
        criteriaQuery.orderBy(sortToOrder(sort, criteriaBuilder(), root));

        TypedQuery<Post> query = getEntityManager().createQuery(criteriaQuery);
        query.setFirstResult(start);
        query.setMaxResults(rows);

        return query.getResultList();
    }

    private Long countAllByPublished(boolean isPublished) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        Root<Post> root = q.from(Post.class);
        root.join("author");

        Path<Boolean> published = root.get("published");
        Predicate predicate = cb.equal(published, Boolean.valueOf(isPublished));
        q.select(cb.count(root));
        q.where(predicate);

        TypedQuery<Long> query = getEntityManager().createQuery(q);
        return query.getSingleResult();
    }

    /**
     * Convert our Sort objects into something that is understood by JPA
     * @param sort
     * @param criteriaBuilder
     * @param root
     * @return
     */
    private Order sortToOrder(Sort sort, CriteriaBuilder criteriaBuilder, Root<?> root) {
        return sort.isAscending() ? criteriaBuilder.asc(root.get(sort.getFieldAsColumnName()))
                : criteriaBuilder.desc(root.get(sort.getFieldAsColumnName()));
    }
}
