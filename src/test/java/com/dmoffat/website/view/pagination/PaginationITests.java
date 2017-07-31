package com.dmoffat.website.view.pagination;

import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.fail;

/**
 * Tests pagination related functionality
 *
 * todo: don't rely on existing database state.
 *
 * @author danielmoffat
 */
public class PaginationITests extends IntegrationTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void test() throws Exception {
        Page<Post> postPage = blogService.findAllPosts(PageRequest.firstPage());

        System.out.println(postPage);

        PageRequest secondPage = postPage.nextPage();

        System.out.println(blogService.findAllPosts(secondPage));

        fail("Finish me.");
    }
}
