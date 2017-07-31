package com.dmoffat.website.view.pagination;

import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.fail;

/**
 * Tests pagination related functionality
 *
 * @author danielmoffat
 */
public class PaginationITests extends IntegrationTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void test() throws Exception {
        PageRequest request = new PageRequestImpl(1);
        System.out.println(blogService.findAllPosts(PageRequest.firstPage()));
        fail("Finish me.");
    }
}
