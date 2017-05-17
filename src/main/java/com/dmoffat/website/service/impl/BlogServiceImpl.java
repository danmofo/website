package com.dmoffat.website.service.impl;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.util.time.TimeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
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
    private TimeProvider timeProvider;

    private Parser markdownParser;
    private HtmlRenderer markdownRenderer;

    @Autowired
    public BlogServiceImpl(PostDao postDao, CommentDao commentDao, TagDao tagDao, TimeProvider timeProvider) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
        this.timeProvider = timeProvider;
        this.markdownParser = Parser.builder().build();
        this.markdownRenderer = HtmlRenderer.builder().build();
    }

    @Override
    public List<Post> findAllPosts() {
        return postDao.findAllPosts(true);
    }

    @Override
    public void save(Post post) {
        Objects.requireNonNull(post, "post cannot be null.");

        post.setHtmlContent(markdownRenderer.render(markdownParser.parse(post.getContent())));

        postDao.create(post);
    }

    @Override
    public Tag findTagByValue(String value) {
        Objects.requireNonNull("value cannot be null");

        Tag tag = tagDao.findOneByValue(value);

        // If the tag doesn't exist, return a new object representing it. If this
        // is added to a post, it will be persisted automatically.
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
        Objects.requireNonNull(post, "post cannot be null.");

        return postDao.update(post);
    }

    @Override
    public void publish(Post post) {
        Objects.requireNonNull(post, "post cannot be null");

        post.setPublished(true);
        postDao.update(post);
    }

    @Override
    public void hide(Post post) {
        Objects.requireNonNull(post, "post cannot be null");

        post.setPublished(false);
        postDao.update(post);
    }

    @Override
    public void addTagTo(Post post, Tag tag) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(tag, "tag cannot be null");

        post.addTag(tag);
        postDao.update(post);
    }

    @Override
    public void addTagsTo(Post post, List<Tag> tags) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(tags, "tags cannot be null");

        tags.forEach(tag -> post.addTag(tag));
        postDao.update(post);
    }

    @Override
    public void removeTagFrom(Post post, Tag tag) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(tag, "tag cannot be null");

        post.removeTag(tag);
        postDao.update(post);
    }

    @Override
    public void removeTagsFrom(Post post, List<Tag> tags) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(tags, "tags cannot be null");

        tags.forEach(tag -> post.removeTag(tag));
        postDao.update(post);
    }

    @Override
    public void addCommentTo(Post post, Comment comment) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(comment, "comment cannot be null");

        post.addComment(comment);
        comment.setPost(post);
        postDao.update(post);
    }

    @Override
    public void removeCommentFrom(Post post, Comment comment) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(comment, "comment cannot be null");

        post.removeComment(comment);
        comment.setPost(null);
        postDao.update(post);
    }

    @Override
    public void remove(Comment comment) {
        Objects.requireNonNull(comment, "comment cannot be null");

        comment.setPost(null);
        commentDao.delete(comment);
    }

    @Override
    public List<Tag> findAllTags() {
        return tagDao.findAll();
    }

    @Override
    public List<Comment> findAllComments() {
        return commentDao.findAll();
    }

    @Override
    public Post findPostById(Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        return postDao.read(id);
    }

    @Override
    public List<Post> findPostByAuthor(String authorName) {
        Objects.requireNonNull(authorName, "authorName cannot be null");

        return postDao.findAllPostsByAuthor(authorName, true);
    }

    @Override
    public List<Post> findPostByDate(LocalDateTime date) {
        Objects.requireNonNull(date, "date cannot be null");

        return postDao.findAllPostsByDate(date, true);
    }

    @Override
    public List<Post> findPostBetween(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "startDate cannot be null");
        Objects.requireNonNull(end, "endDate cannot be null");

        return postDao.findAllPostsByDateBetween(start, end, true);
    }

    @Override
    public List<Post> findRecentPosts() {
        return findPostBetween(timeProvider.now().minusMonths(1), timeProvider.now());
    }
}
