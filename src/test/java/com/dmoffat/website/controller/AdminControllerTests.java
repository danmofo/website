package com.dmoffat.website.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.fail;

/**
 * @author dan
 */
// todo: add tests for post content -> html_content conversion, json responses for different scenarios.
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest({"auth.secret=test_secret"})
public class AdminControllerTests {
    @Test
    public void todo() throws Exception {
        fail();
    }
}
