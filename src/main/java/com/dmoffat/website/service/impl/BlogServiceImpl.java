package com.dmoffat.website.service.impl;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import com.dmoffat.website.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author dan
 */
@Transactional
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    private PostDao postDao;
    private CommentDao commentDao;
    private TagDao tagDao;

    @Autowired
    public BlogServiceImpl(PostDao postDao, CommentDao commentDao, TagDao tagDao) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
    }

    @Override
    public List<Post> findAllPosts() {
        return postDao.findAllPosts(true);
    }

    @Override
    public void save(Post post) {
        Objects.requireNonNull(post, "post cannot be null.");

        postDao.create(post);
    }

    @Override
    public Tag findTagByValue(String value) {
        Objects.requireNonNull("value cannot be null");

        Tag tag = tagDao.findOneByValue(value);

        // If the tag doesn't exist, return a new object representing it. If this
        // is added a post, it will be persisted automatically.
        if(tag == null) {
            return new Tag.Builder().value(value).build();
        }

        return tag;
    }

    @Override
    public Tag findTagById(Long id) {
        Objects.requireNonNull(id, "id cannot be null.");

        return tagDao.read(id);
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public void publish(Post post) {

    }

    @Override
    public void hide(Post post) {

    }

    @Override
    public void addTagTo(Post post, Tag tag) {

    }

    @Override
    public void addTagsTo(Post post, List<Tag> tags) {

    }

    @Override
    public void removeTagFrom(Post post, Tag tag) {

    }

    @Override
    public void removeTagsFrom(Post post, List<Tag> tags) {

    }

    @Override
    public void addCommentTo(Post post, Comment comment) {

    }

    @Override
    public void removeCommentFrom(Post post, Comment comment) {

    }

    @Override
    public void remove(Comment comment) {

    }

    @Override
    public List<Tag> findAllTags() {
        return null;
    }

    @Override
    public List<Comment> findAllComments() {
        return null;
    }

    @Override
    public Post findPostById(Long id) {
        return null;
    }

    @Override
    public Post findPostByAuthor(String authorName) {
        return null;
    }

    @Override
    public Post findPostByDate(LocalDateTime date) {
        return null;
    }

    @Override
    public Post findPostBetween(LocalDateTime start, LocalDateTime end) {
        return null;
    }
}
