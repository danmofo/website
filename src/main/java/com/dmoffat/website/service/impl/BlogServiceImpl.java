package com.dmoffat.website.service.impl;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PatchDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.*;
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
 * todo: test all of the dao / service methods that call the non-PageRequest variant with default values
 * todo: write generic count query (that includes the where clause!)
 * todo: add a default page request value if it's null
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
            pageRequest = PageRequest.firstPage();
        }

        List<Post> posts = postDao.findAll(pageRequest.getStartCount(), pageRequest.getRows());
        Long totalRows = postDao.count();

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findAllPosts() {
        return findAllPosts(PageRequest.firstPage());
    }

    @Override
    public Page<Post> findAllPublishedPosts() {
        return findAllPublishedPosts(PageRequest.firstPage());
    }

    @Override
    public Page<Post> findAllPublishedPosts(PageRequest pageRequest) {
        if(pageRequest == null) {
            pageRequest = PageRequest.firstPage();
        }

        List<Post> posts = postDao.findAllPublishedPosts(pageRequest.getStartCount(), pageRequest.getRows());
        Long totalRows = postDao.countPublishedPosts();

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findAllPostsWithTags() {
        return findAllPostsWithTags(PageRequest.firstPage());
    }

    @Override
    public Page<Post> findAllPostsWithTags(PageRequest pageRequest) {
        List<Post> posts = postDao.findAllPostsWithTags();
        Long totalRows = postDao.count();

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findAllPostsWithTagsAndComments() {
        return findAllPostsWithTagsAndComments(PageRequest.firstPage());
    }

    @Override
    public Page<Post> findAllPostsWithTagsAndComments(PageRequest pageRequest) {
        List<Post> posts = postDao.findAllPostsWithTagsAndComments();
        Long totalRows = postDao.count();
        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findAllPostsWithTagsAndCommentsAndDiffs() {
        return findAllPostsWithTagsAndCommentsAndDiffs(PageRequest.firstPage());
    }

    @Override
    public Page<Post> findAllPostsWithTagsAndCommentsAndDiffs(PageRequest pageRequest) {
        List<Post> posts = postDao.findAllPostsWithTagsAndCommentsAndDiffs();
        Long totalRows = postDao.count();

        return new PageImpl<>(posts, pageRequest, totalRows);
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
    public Page<Tag> findAllTags() {
        return findAllTags(PageRequest.firstPage());
    }

    @Override
    public Page<Tag> findAllTags(PageRequest pageRequest) {
//        return tagDao.findAll();
        return null;
    }

    @Override
    public Page<Comment> findAllComments() {
        return findAllComments(PageRequest.firstPage());
    }

    @Override
    public Page<Comment> findAllComments(PageRequest pageRequest) {
//        return commentDao.findAll();
        return null;
    }

    @Override
    public Post findPostById(Long id) {
        Objects.requireNonNull(id, "id cannot be null");

        return postDao.read(id);
    }

    @Override
    public Page<Post> findPostByAuthor(String authorName) {
        return findPostByAuthor(authorName, PageRequest.firstPage());
    }

    @Override
    public Page<Post> findPostByAuthor(String authorName, PageRequest pageRequest) {
        Objects.requireNonNull(authorName, "authorName cannot be null");

        List<Post> posts = postDao.findAllPostsByAuthor(authorName, true);
        Long totalRows = postDao.countPostsByAuthor(authorName);

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findPostByDate(LocalDateTime date) {
        return findPostByDate(date, PageRequest.firstPage());
    }

    @Override
    public Page<Post> findPostByDate(LocalDateTime date, PageRequest pageRequest) {
        Objects.requireNonNull(date, "date cannot be null");

        List<Post> posts = postDao.findAllPostsByDate(date, true);
        Long totalRows = postDao.countPostsByDate(date);

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findPostBetween(LocalDateTime start, LocalDateTime end) {
        return findPostBetween(start, end, PageRequest.firstPage());
    }

    @Override
    public Page<Post> findPostBetween(LocalDateTime start, LocalDateTime end, PageRequest pageRequest) {
        Objects.requireNonNull(start, "startDate cannot be null");
        Objects.requireNonNull(end, "endDate cannot be null");

        List<Post> posts = postDao.findAllPostsByDateBetween(start, end, true);
        Long totalRows = postDao.countPostsByDateBetween(start, end);

        return new PageImpl<>(posts, pageRequest, totalRows);
    }

    @Override
    public Page<Post> findRecentPosts() {
        return findPostBetween(timeProvider.now().minusMonths(1), timeProvider.now(), PageRequest.firstPage());
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
