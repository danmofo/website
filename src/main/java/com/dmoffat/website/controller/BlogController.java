package com.dmoffat.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Controller
public class BlogController {

    @RequestMapping(path = "/home")
    public String home() {
        return "";
    }
}
