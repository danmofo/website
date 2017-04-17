package com.dmoffat.website.controller;

import com.dmoffat.website.model.Post;
import com.dmoffat.website.service.BlogService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Controller
public class BlogController {

    private static final Logger logger = LogManager.getLogger(BlogController.class);

    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(path = "/home")
    public String home(Model m) {

        Post post = new Post.Builder()
                .author("Daniel Moffat")
                .title("Hello world!")
                .content("My cool post.")
                .build();

        post.addTag(blogService.findTagByValue("web"));
        blogService.save(post);

        List<Post> posts = blogService.findAllPosts();

        m.addAttribute("posts", posts);

        return "home";
    }
}
