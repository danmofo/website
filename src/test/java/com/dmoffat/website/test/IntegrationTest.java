package com.dmoffat.website.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

/**
 * A base integration testing class.
 *
 * By extending this class you have access to a fully mocked Spring web environment via 'mockMvc'.
 *
 * @author danielmoffat
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest({"auth.secret=test_secret"})
@Transactional
public abstract class IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;
}
