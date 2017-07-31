package com.dmoffat.website.model;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 *
 * Test the BaseEntity class and the created / updated field functionality it provides
 *
 * @author dan
 */

public class BaseEntityTests extends IntegrationTest {

    private Post post;

    @Autowired
    private PostDao postDao;

    @Before
    public void setUp() throws Exception {
        Author author = new Author("Daniel Moffat");
        post = new Post.Builder().title("test").author(author).content("Content").permalink("content").build();

        postDao.create(post);
    }

    @Test
    public void testCreatedDateIsSetOnPersist() throws Exception {
        assertNotNull(post.getCreated());
    }

    @Test
    public void testUpdatedDateIsSetOnUpdate() throws Exception {
        post.setTitle("An updated title.");
        postDao.update(post);

        // Force an update, by manually calling flush().
        postDao.getEntityManager().flush();

        assertNotNull(post.getUpdated());
    }

    @Test
    public void testIdIsSetOnPersist() throws Exception {
        assertNotNull(post.getId());
    }
}
