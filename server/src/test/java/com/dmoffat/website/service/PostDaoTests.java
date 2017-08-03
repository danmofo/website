package com.dmoffat.website.service;

import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.model.Author;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

/**
 * todo: don't make this rely on existing database state
 *
 * Test drives the PostDao to make sure it returns the correct results.
 *
 * @author danielmoffat
 */
public class PostDaoTests extends IntegrationTest {

    @Autowired
    private PostDao postDao;

    private Post post;
    private LocalDateTime createdDate;

    @Before
    public void setUp() throws Exception {
        createdDate = LocalDateTime.of(2017, 1, 1, 0, 0);
        post = new Post.Builder()
                    .author(new Author("Once"))
                    .title("The title")
                    .permalink("perma-permalink")
                    .content("Ze content")
                    .created(createdDate)
                    .build();

        postDao.create(post);
    }

    @Test
    public void shouldReturnTheCorrectAmountOfTotalPosts() throws Exception {
        Long totalPosts = postDao.count();
        assertTrue(12 == totalPosts);
    }

    @Test
    public void shouldReturnTheCorrectAmountOfPostsByAuthor() throws Exception {
        Long count = postDao.countPostsByAuthor("Once");
        assertTrue(count == 1);
    }

    @Test
    public void shouldReturnTheCorrectAmountOfPostsByOnASpecificDate() throws Exception {
        Long count = postDao.countPostsByDate(createdDate);
        assertTrue(count == 1);
    }

    @Test
    public void shouldReturnTheCorrectAmountOfPostsCreatedBetweenTwoDates() throws Exception {
        LocalDateTime start = createdDate.minusSeconds(1);
        LocalDateTime end = createdDate.plusSeconds(1);
        Long count = postDao.countPostsByDateBetween(start, end);
        assertTrue(count == 1);
    }
}


