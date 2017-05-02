package com.dmoffat.website.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


/**
 * Test the BlogController and ensure it returns the correct response to various different requests.
 *
 * todo: don't start up the entire application, following this: https://spring.io/guides/gs/testing-web/
 * todo: refactor tests that duplicate code
 *
 * @author dan
 */

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class BlogControllerTests {

    @Autowired
    private BlogController blogController;

    @Autowired
    private MockMvc mockMvc;

    // Make sure all autowiring works as expected
    @Test
    public void testContextLoads() throws Exception {
        assertNotNull(blogController);
    }

    @Test
    public void testHome() throws Exception {
        this.mockMvc
                .perform(get("/blog/"))
                .andDo(print())
                .andExpect(view().name("home"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Home")));
    }

    @Test
    public void testArchive() throws Exception {
        this.mockMvc
                .perform(get("/blog/archive"))
                .andDo(print())
                .andExpect(view().name("archive"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Archive"))));
    }

    @Test
    public void testListTags() throws Exception {
        this.mockMvc
                .perform(get("/blog/tags"))
                .andDo(print())
                .andExpect(view().name("tags"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Tags"))));
    }

    @Test
    public void testListByYear() throws Exception {
        this.mockMvc
                .perform(get("/blog/2017/"))
                .andDo(print())
                .andExpect(view().name("year"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Year"))));
    }

    @Test
    public void testListByYearAndMonth() throws Exception {
        this.mockMvc
                .perform(get("/blog/2017/05/"))
                .andDo(print())
                .andExpect(view().name("month"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(("Year and month"))));
    }
}
