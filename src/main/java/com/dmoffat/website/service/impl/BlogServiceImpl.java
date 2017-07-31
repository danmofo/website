package com.dmoffat.website.service.impl;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PatchDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Patch;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.util.time.TimeProvider;
import com.dmoffat.website.view.pagination.Page;
import com.dmoffat.website.view.pagination.PageImpl;
import com.dmoffat.website.view.pagination.PageRequest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import name.fraser.neil.plaintext.diff_match_patch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.LinkedList;
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
    private PatchDao patchDao;

    private Parser markdownParser;
    private HtmlRenderer markdownRenderer;

    @Autowired
    public BlogServiceImpl(PostDao postDao, CommentDao commentDao, TagDao tagDao, TimeProvider timeProvider, PatchDao patchDao) {
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
        this.timeProvider = timeProvider;
        this.patchDao = patchDao;
        this.markdownParser = Parser.builder().build();
        this.markdownRenderer = HtmlRenderer.builder().build();
    }

    @Override
    public Page<Post> findAllPosts(PageRequest pageRequest) {
        if(pageRequest == null) {
            return Page.emptyPage();
        }

        List<Post> posts = postDao.findAllPostsWithTags((pageRequest.getPage() - 1) * 10, pageRequest.getRows());
        Long totalRows = postDao.count();

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public List<Post> findAllPosts() {
        return postDao.findAll();
    }

    @Override
    public List<Post> findAllPostsWithTags() {
        return postDao.findAllPostsWithTags();
    }

    @Override
    public List<Post> findAllPostsWithTagsAndComments() {
        return postDao.findAllPostsWithTagsAndComments();
    }

    @Override
    public List<Post> findAllPostsWithTagsAndCommentsAndDiffs() {
        return postDao.findAllPostsWithTagsAndCommentsAndDiffs();
    }

    @Override
    public void save(Post post) {
        Objects.requireNonNull(post, "post cannot be null.");

        post.setHtmlContent(markdownRenderer.render(markdownParser.parse(post.getContent())));
        post.setOriginalContent(post.getContent());

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
    public Post update(Post post, String originalContent) {
        // Determine if there are differences
        diff_match_patch diffMatchPatch = new diff_match_patch();
        LinkedList<diff_match_patch.Diff> diffs = diffMatchPatch.diff_main(originalContent, post.getContent());

        // If both strings are equal, there is 1 "diff", which just states they are equal
        if(diffs.size() > 1 && diffs.get(0).operation.equals(diff_match_patch.Operation.EQUAL)) {
            // Create a patch, containing the differences between the current version and the new version
            Patch patch = new Patch();
            patch.setText(diffMatchPatch.patch_toText(diffMatchPatch.patch_make(diffs)));
            patch.setModified(timeProvider.now());
            patch.setPost(post);
            post.addDiff(patch);
            patchDao.create(patch);
        }

        return update(post);
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
        commentDao.create(comment);
    }

    @Override
    public void removeCommentFrom(Post post, Comment comment) {
        Objects.requireNonNull(post, "post cannot be null");
        Objects.requireNonNull(comment, "comment cannot be null");

        post.removeComment(comment);
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

    @Override
    public boolean archive(Long postId) {
        Objects.requireNonNull(postId, "postId cannot be null.");

        Post post = findPostById(postId);

        if(post == null) {
            return false;
        }

        post.setArchived(true);
        postDao.update(post);
        return true;
    }
}
