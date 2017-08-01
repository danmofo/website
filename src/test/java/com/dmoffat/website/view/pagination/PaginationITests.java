package com.dmoffat.website.view.pagination;

import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.test.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static junit.framework.TestCase.fail;

/**
 * Tests pagination related functionality against a real database
 *
 * todo: don't rely on existing database state - create it from scatch
 *
 * @author danielmoffat
 */
public class PaginationITests extends IntegrationTest {

    @Autowired
    private BlogService blogService;

    @Test
    public void test() throws Exception {
        fail("Finish me.");
    }
}
