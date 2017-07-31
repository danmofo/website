package com.dmoffat.website.view.pagination;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PatchDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.service.impl.BlogServiceImpl;
import com.dmoffat.website.test.UnitTest;
import com.dmoffat.website.util.time.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Tests pagination related functionality
 *
 * @author danielmoffat
 */
public class PaginationTests extends UnitTest {
    private BlogService blogService;

    @Mock PostDao postDao;
    @Mock TagDao tagDao;
    @Mock CommentDao commentDao;
    @Mock PatchDao patchDao;
    @Mock TimeProvider timeProvider;

    @Before
    public void setUp() throws Exception {
        blogService = new BlogServiceImpl(postDao, commentDao, tagDao, timeProvider, patchDao);
    }

    @Test
    public void test() throws Exception {
        PageRequest request = new PageRequestImpl(1);

        Page<Post> postPage = blogService.findAllPosts(request);

        System.out.println(postPage);

    }
}
