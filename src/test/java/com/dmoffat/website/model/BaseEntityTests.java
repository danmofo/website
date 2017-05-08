package com.dmoffat.website.model;

import com.dmoffat.website.BlogApplication;
import com.dmoffat.website.dao.PostDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertNotNull;

/**
 *
 * Test the BaseEntity class and the created / updated field functionality it provides
 *
 * @author dan
 */

@SpringBootTest(
        classes = BlogApplication.class,
        properties = {"auth.secret=test_secret"})
@Transactional
@RunWith(SpringRunner.class)
public class BaseEntityTests {

    private Post post;

    @Autowired
    private PostDao postDao;

    @Before
    public void setUp() throws Exception {
        post = new Post.Builder().author("Daniel").content("Content").permalink("content").build();

        postDao.create(post);
    }

    @Test
    public void testCreatedDateIsSetOnPersist() throws Exception {
        assertNotNull(post.getCreated());
    }

    @Test
    public void testUpdatedDateIsSetOnUpdate() throws Exception {
        post.setAuthor("John");
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
