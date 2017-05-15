package com.dmoffat.website.service;

import com.dmoffat.website.BlogApplication;
import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Integration tests that test drive the BlogService, using the same database as production.
 *
 * Each test doesn't rely on the state of the database, instead it creates and inserts entities
 * as needed before each test. Many of these tests use service methods themselves to create the required
 * state, which is not ideal.
 *
 * todo: use an in memory database purely for testing
 * todo: add profile for testing with a different config to production
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = BlogApplication.class,
		properties = {"auth.secret=test_secret"})
@Transactional
public class BlogServiceTests {

    @Autowired
    private BlogService blogService;

    private Post post;
    private Post publishedPost;
    private Post postWithTags;
    private Post postWithComment;

    private Tag tag;
    private Tag tag2;
    private Tag tag3;

    private Comment basicComment;

	@Before
	public void setUp() throws Exception {
		this.post = new Post.Builder()
				.author("Dan")
				.content("My test post")
				.title("My title")
				.permalink("post")
				.build();

		blogService.save(post);

		this.publishedPost = new Post.Builder()
				.author("Dan")
				.content("My test post")
				.title("My title")
				.published(true)
				.permalink("published-post")
				.build();

		blogService.save(publishedPost);

		this.postWithTags = new Post.Builder()
				.author("Danz")
				.content("My test post 2")
				.title("My title2")
				.permalink("post-with-tags")
				.build();

		this.tag = new Tag.Builder().value("testtag").build();
		this.tag2 = new Tag.Builder().value("testtag2").build();
		this.tag3 = new Tag.Builder().value("testtag3").build();

		this.postWithTags.addTag(tag);
		this.postWithTags.addTag(tag2);
		this.postWithTags.addTag(tag3);

		blogService.save(postWithTags);

		this.basicComment = new Comment.Builder().name("Daniel").post(post).content("This is a great post.").build();

		this.postWithComment = new Post.Builder()
				.author("Dan")
				.content("My test post")
				.title("My title")
				.permalink("foobar").build();
		this.postWithComment.addComment(basicComment);

		blogService.save(postWithComment);
	}

	// The post is inserted before each test.
	@Test
	public void savePost() {
		assertNotNull(post.getId());

		// Id is only null when it hasn't been persisted to the DB
		Post post2 = new Post.Builder()
					.author("D")
					.content("asdf")
					.title("asddd")
					.permalink("asdf")
					.build();

		assertNull(post2.getId());

		blogService.save(post2);
		assertNotNull(post2.getId());
	}

	@Test
	public void updatePost() throws Exception {
		post.setAuthor("Chrissy");
		blogService.update(post);

		assertNotNull(blogService.findPostByAuthor("Chrissy"));
	}

	@Test
	public void publishPost() throws Exception {
		blogService.publish(post);
		assertTrue(post.isPublished());
	}

	@Test
	public void hidePost() throws Exception {
		assertTrue(publishedPost.isPublished());
		blogService.hide(publishedPost);
		assertFalse(publishedPost.isPublished());
	}

	@Test
	public void addTagToPost() throws Exception {
		blogService.addTagTo(post, new Tag.Builder().value("web").build());
		assertTrue(post.getTags().size() == 1);
	}

	@Test
	public void addMultipleTagsToPost() throws Exception {
		List<Tag> tags = new ArrayList<>();
		tags.add(new Tag.Builder().value("web").build());
		tags.add(new Tag.Builder().value("web2").build());
		tags.add(new Tag.Builder().value("web3").build());

		blogService.addTagsTo(post, tags);

		assertTrue(post.getTags().size() == 3);
	}

	@Test
	public void removeTagFromPost() throws Exception {
		blogService.removeTagFrom(postWithTags, tag);
		assertTrue(postWithTags.getTags().size() == 2);
	}

	@Test
	public void removeMultipleTagsFromPost() throws Exception {
		blogService.removeTagsFrom(postWithTags, Arrays.asList(tag, tag2, tag3));
		assertTrue(postWithTags.getTags().size() == 0);
	}

	@Test
	public void addCommentToPost() throws Exception {
		blogService.addCommentTo(post, basicComment);
		assertTrue(post.getComments().size() == 1);
	}

	@Test
	public void removeCommentFromPost() throws Exception {
		blogService.removeCommentFrom(postWithComment, basicComment);
		assertTrue(postWithComment.getComments().size() == 0);
	}

	@Test
	public void removeComment() throws Exception {
		blogService.remove(basicComment);
		assertNull(basicComment.getPost());
	}

	@Test
	public void findTag() throws Exception {
		assertNotNull(blogService.findTagById(tag.getId()));
		assertNotNull(blogService.findTagByValue(tag.getValue()));
	}

	@Test
	public void findPost() throws Exception {
		assertNotNull(blogService.findPostByAuthor(post.getAuthor()));
		assertNotNull(blogService.findPostBetween(post.getCreated().minusDays(1), post.getCreated().plusDays(1)));
		assertNotNull(blogService.findPostByDate(post.getCreated()));
		assertNotNull(blogService.findPostById(post.getId()));
		assertNotNull(blogService.findRecentPosts());
	}
}
