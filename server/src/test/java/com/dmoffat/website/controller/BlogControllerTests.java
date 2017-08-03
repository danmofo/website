package com.dmoffat.website.controller;

import com.dmoffat.website.test.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test the BlogController and ensure it returns the correct response to various different requests.
 *
 * todo: don't start up the entire application, following this: https://spring.io/guides/gs/testing-web/
 * todo: refactor tests that duplicate code
 *
 * @author dan
 */

public class BlogControllerTests extends IntegrationTest {

    @Autowired
    private BlogController blogController;

    // Make sure all autowiring works as expected
    @Test
    public void shouldWireBeans() throws Exception {
        assertNotNull(blogController);
    }

    @Test
    public void shouldReturnTheHomePage() throws Exception {
        this.mockMvc
                .perform(get("/blog/"))
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Home")));
    }

    @Test
    public void shouldReturnArchivedPosts() throws Exception {
        this.mockMvc
                .perform(get("/blog/archive"))
                .andExpect(view().name("archive"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Archive"))));
    }

    @Test
    public void shoudldReturnAListOfTags() throws Exception {
        this.mockMvc
                .perform(get("/blog/tags"))
                .andExpect(view().name("tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Tags"))));
    }

    @Test
    public void shouldReturnPostsByYear() throws Exception {
        this.mockMvc
                .perform(get("/blog/2017/"))
                .andExpect(view().name("year"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Year"))));
    }

    @Test
    public void shouldReturnPostsByYearAndMonth() throws Exception {
        this.mockMvc
                .perform(get("/blog/2017/05/"))
                .andExpect(view().name("month"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Year and month"))));
    }
}
