package com.dmoffat.website.model;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
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
    public void testCreatedIsNotSetWhenCreatedIsPresent() throws Exception {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        Post post = new Post.Builder()
                .title("Foo bar")
                .author(new Author("Daniel Moffat"))
                .content("This is the content")
                .permalink("sdahjsdajkdsajk3")
                .created(dateTime)
                .build();

        postDao.create(post);
        assertEquals(dateTime, post.getCreated());
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
