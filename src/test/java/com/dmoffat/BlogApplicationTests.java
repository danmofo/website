package com.dmoffat;

import com.dmoffat.website.BlogApplication;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Integration tests that test drive the app.
 *
 * todo: use an in memory database purely for testing
 * todo: add some transactional tests
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApplication.class)
public class BlogApplicationTests {

    @Autowired
    private BlogService blogService;

    @Test
	public void testFindAllPosts() {
	    List<Post> posts = blogService.findAllPosts();

	    assertTrue(posts.size() > 0);
	}

}
