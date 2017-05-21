package com.dmoffat.website.controller;

import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author danielmoffat
 */
@Controller
public class BlogController {
    private static final Logger logger = LogManager.getLogger(BlogController.class);

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // List the latest blog posts
    @GetMapping(path = "/blog/")
    public String home(Model m) {
        List<Post> posts = blogService.findAllPosts();

        m.addAttribute("posts", posts);

        return "home";
    }

    // List all blog posts
    @GetMapping(path = "/blog/archive")
    public String archive(Model m) {
        m.addAttribute("posts", blogService.findAllPosts());
        return "archive";
    }

    // List all tags
    @GetMapping(path = "/blog/tags")
    public String tags() {
        return "tags";
    }

    @GetMapping(path = "/blog/{year}/")
    public String listPostsByYear(@PathVariable("year") Integer year) {
        System.out.println("Year: " + year);
        return "year";
    }

    @GetMapping(path = "/blog/{year}/{month}/")
    public String listPostsByYearAndMonth(@PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        System.out.println("Year: " + year + ", month: " + month);
        return "month";
    }
}
