package com.dmoffat.website.service;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Author;
import com.dmoffat.website.model.Comment;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.Tag;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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
 * todo: test blog post with markdown content...
 * todo: test applying the patches to content to assert that the patching library actually works as intended.
 * todo: change time to be static so recent posts can be tested correctly.
 */
public class BlogServiceTests extends IntegrationTest {

	@PersistenceContext private EntityManager entityManager;
    @Autowired private BlogService blogService;
    @Autowired private PostDao postDao;

    private Post post;
    private Post publishedPost;
    private Post postWithTags;
    private Post postWithComment;
    private Post postWithRevision;
    private Post postWithMultipleRevisions;
    private Post postWithFakeCreatedDate;
    private Post postWithAuthor;

    private LocalDateTime fakeCreatedDate = LocalDateTime.of(2017, 1, 1, 0, 0);

    private Tag tag;
    private Tag tag2;
    private Tag tag3;

    private Comment basicComment;

	@Before
	public void setUp() throws Exception {
		this.post = new Post.Builder()
				.author(new Author("Daniel Moffat"))
				.content("My test post")
				.title("My title")
				.permalink("post")
				.build();

		blogService.save(post);

		this.publishedPost = new Post.Builder()
				.author(new Author("Daniel Moffat"))
				.content("My test post")
				.title("My title")
				.published(true)
				.permalink("published-post")
				.build();

		blogService.save(publishedPost);

		this.postWithTags = new Post.Builder()
				.author(new Author("Daniel Moffat"))
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
				.author(new Author("Daniel Moffat"))
				.content("My test post")
				.title("My title")
				.permalink("foobar")
				.build();
		this.postWithComment.addComment(basicComment);

		blogService.save(postWithComment);

		this.postWithRevision = new Post.Builder()
				.author(new Author("Daniel Moffat"))
				.content("Revision one")
				.title("Post with no revisions")
				.permalink("post-with-revisions")
				.build();

		blogService.save(postWithRevision);

		String originalContent = "Revision one";

		this.postWithMultipleRevisions = new Post.Builder()
				.author(new Author("Daniel Moffat"))
				.content(originalContent)
				.title("Post with multiple revisions")
				.permalink("post-with-revisions-23455")
				.build();

		blogService.save(postWithMultipleRevisions);

		this.postWithMultipleRevisions.setContent("Revision two");
		blogService.update(postWithMultipleRevisions, originalContent);
		this.postWithMultipleRevisions.setContent("Revision three");
		blogService.update(postWithMultipleRevisions, "Revision two");

		this.postWithFakeCreatedDate = new Post.Builder()
				.author(new Author("Daniel Moffat"))
				.title("Ze title.")
				.content("Some content...")
				.permalink("asd-asda-ffsadas")
				.created(fakeCreatedDate)
				.build();

		blogService.save(postWithFakeCreatedDate);

		this.postWithAuthor = new Post.Builder()
				.author(new Author("Mr Once Only"))
				.title("Another original title.")
				.content("Some content...")
				.permalink("perma1234")
				.build();

		blogService.save(postWithAuthor);
	}

	@Test
	public void shouldCreateARevisionWhenUpdatingPostContent() throws Exception {
		String originalContent = this.postWithRevision.getContent();
		this.postWithRevision.setContent("Revision two");
		blogService.update(postWithRevision, originalContent);
		assertTrue(postWithRevision.getDiffs().size() > 0);
	}

	@Test
	public void shouldntCreateARevisionWhenUpdatingPostTitle() throws Exception {
		String originalContent = this.postWithRevision.getContent();
		this.postWithRevision.setTitle("This is a brand new title!");
		blogService.update(postWithRevision, originalContent);
		assertTrue(postWithRevision.getDiffs().size() == 0);
	}

	@Test
	public void shouldReturnTheCorrectVersionWhenPostHasRevisions() throws Exception {
		String originalContent = this.postWithRevision.getContent();
		this.postWithRevision.setContent("Revision two");
		blogService.update(postWithRevision, originalContent);
		assertEquals(2, postWithRevision.revisionCount());
	}

	@Test
	public void shouldHaveTheDefaultRevisionCount() throws Exception {
		assertEquals(1, this.postWithRevision.revisionCount());
	}

	// todo(dan): maybe add some more complex examples that aren't just replacing a word.
	@Test
	public void shouldReturnTheCorrectVersion() throws Exception {
		assertEquals("Revision one", postWithMultipleRevisions.version());
		assertEquals("Revision two", postWithMultipleRevisions.version(2));
		assertEquals("Revision three", postWithMultipleRevisions.version(3));
	}

	@Test
	public void shouldSaveThePost() {
		assertNotNull(post.getId());

		// Id is only null when it hasn't been persisted to the DB
		Post post2 = new Post.Builder()
					.author(new Author("Daniel Moffat"))
					.content("asdf")
					.title("asddd")
					.permalink("asdf")
					.build();

		assertNull(post2.getId());

		blogService.save(post2);
		assertNotNull(post2.getId());
	}

	@Test
	public void shouldUpdateThePost() throws Exception {
		post.setAuthor(new Author("Chrissy"));
		blogService.update(post);

		assertNotNull(blogService.findPostByAuthor("Chrissy"));
	}

	@Test
	public void shouldPublishThePost() throws Exception {
		blogService.publish(post);
		assertTrue(post.isPublished());
	}

	@Test
	public void shouldHideThePost() throws Exception {
		assertTrue(publishedPost.isPublished());
		blogService.hide(publishedPost);
		assertFalse(publishedPost.isPublished());
	}

	@Test
	public void shouldAddATagToAPost() throws Exception {
		blogService.addTagTo(post, new Tag.Builder().value("web").build());
		assertTrue(post.getTags().size() == 1);
	}

	@Test
	public void shouldAddMultipleTagsToPost() throws Exception {
		List<Tag> tags = new ArrayList<>();
		tags.add(new Tag.Builder().value("web").build());
		tags.add(new Tag.Builder().value("web2").build());
		tags.add(new Tag.Builder().value("web3").build());

		blogService.addTagsTo(post, tags);

		assertTrue(post.getTags().size() == 3);
	}

	@Test
	public void shouldRemoveTagFromPost() throws Exception {
		blogService.removeTagFrom(postWithTags, tag);
		assertTrue(postWithTags.getTags().size() == 2);
	}

	@Test
	public void shouldRemoveMultipleTagsFromPost() throws Exception {
		blogService.removeTagsFrom(postWithTags, Arrays.asList(tag, tag2, tag3));
		assertTrue(postWithTags.getTags().size() == 0);
	}

	@Test
	public void shouldAddCommentToPost() throws Exception {
		blogService.addCommentTo(post, basicComment);
		assertTrue(post.getComments().size() == 1);
	}

	@Test
	public void shouldRemoveCommentFromPost() throws Exception {
		blogService.removeCommentFrom(postWithComment, basicComment);
		assertTrue(postWithComment.getComments().size() == 0);
		entityManager.flush();
		Post post = blogService.findPostById(postWithComment.getId());
		assertTrue(post.getComments().size() == 0);
	}

	@Test
	public void shouldRemoveComment() throws Exception {
		blogService.remove(basicComment);
		assertNull(basicComment.getPost());
	}

	@Test
	public void shouldFindTagById() throws Exception {
		assertNotNull(blogService.findTagById(tag.getId()));
	}

	@Test
	public void shouldFindTagByValue() throws Exception {
		assertNotNull(blogService.findTagByValue(tag.getValue()));
	}

	// todo: finish
	@Test
	public void findRecentPosts() throws Exception {
		assertNotNull(blogService.findRecentPosts());
		fail("Finish me!");
	}

	@Test
	public void shouldFindPostByAuthor() throws Exception {
		List<Post> postByAuthor = blogService.findPostByAuthor("Mr Once Only");
		assertThat(postByAuthor, hasSize(1));
	}

	@Test
	public void shouldFindPostsBetweenTwoDates() throws Exception {
		LocalDateTime start = fakeCreatedDate.minusDays(1);
		LocalDateTime end = fakeCreatedDate.plusDays(1);
		List<Post> posts = blogService.findPostBetween(start, end);

		assertThat(posts, hasSize(1));
	}

	@Test
	public void shouldFindPostsOnASpecificDate() throws Exception {
		List<Post> postByDate = blogService.findPostByDate(postWithFakeCreatedDate.getCreated());
		assertThat(postByDate, hasSize(1));
	}

	@Test
	public void shouldFindPostById() throws Exception {
		Post result = blogService.findPostById(post.getId());
		assertNotNull(result);
	}

	@Test
	public void shouldEditThePost() throws Exception {
		post.setAuthor(new Author("Edited!"));
		blogService.update(post);
		assertTrue(blogService.findPostByAuthor("Edited!") != null);
	}

	@Test
	public void shouldArchiveThePost() throws Exception {
		blogService.archive(post.getId());
		assertTrue(post.isArchived());
	}
}
