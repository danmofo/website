package com.dmoffat.website.controller;

import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.util.JwtsUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Controller
public class BlogController {
    private static final Logger logger = LogManager.getLogger(BlogController.class);

    private BlogService blogService;
    private JwtsUtils jwtsUtils;

    @Autowired
    public BlogController(BlogService blogService, JwtsUtils jwtsUtils) {
        this.blogService = blogService;
        this.jwtsUtils = jwtsUtils;
    }

    // List the latest blog posts
    @RequestMapping(path = "/blog/", method = RequestMethod.GET)
    public String home(Model m) {
        List<Post> posts = blogService.findAllPosts();

        m.addAttribute("posts", posts);

        return "home";
    }

    // List all blog posts
    @RequestMapping(path = "/blog/archive", method = RequestMethod.GET)
    public String archive(Model m) {
        m.addAttribute("posts", blogService.findAllPosts());
        return "archive";
    }

    // List all tags
    @RequestMapping(path = "/blog/tags", method = RequestMethod.GET)
    public String tags() {
        return "tags";
    }

    @RequestMapping(path = "/blog/{year}/", method = RequestMethod.GET)
    public String listPostsByYear(@PathVariable("year") Integer year) {
        System.out.println("Year: " + year);
        return "year";
    }

    @RequestMapping(path = "/blog/{year}/{month}/", method = RequestMethod.GET)
    public String listPostsByYearAndMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        System.out.println("Year: " + year + ", month: " + month);
        return "month";
    }
}
