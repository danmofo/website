package com.dmoffat.website.view.pagination;

import com.dmoffat.website.dao.CommentDao;
import com.dmoffat.website.dao.PatchDao;
import com.dmoffat.website.dao.PostDao;
import com.dmoffat.website.dao.TagDao;
import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.service.impl.BlogServiceImpl;
import com.dmoffat.website.test.UnitTest;
import com.dmoffat.website.util.TestUtils;
import com.dmoffat.website.util.time.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Tests pagination related functionality against mocked dependencies, these don't require a database.
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
    public void shouldHaveZeroTotalPagesWhenNoPostsAreFound() throws Exception {
        when(postDao.findAll()).thenReturn(Collections.emptyList());
        Page<Post> posts = blogService.findAllPosts();

        assertThat(posts.getResults(), hasSize(0));
        assertFalse(posts.hasNextPage());
        assertFalse(posts.hasPrevPage());
    }

    @Test
    public void shouldHaveOnePageWhenTenPostsAreFound() throws Exception {
        when(postDao.findAll(0, 10)).thenReturn(TestUtils.createRandomPosts(10));
        Page<Post> posts = blogService.findAllPosts();

        assertThat(posts.getResults(), hasSize(10));
        assertFalse(posts.hasNextPage());
        assertFalse(posts.hasPrevPage());
        assertTrue(posts.getPageRequest().getPage() == 1);
    }
}
