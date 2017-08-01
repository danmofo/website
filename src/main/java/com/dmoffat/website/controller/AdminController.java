package com.dmoffat.website.controller;

import com.dmoffat.website.model.Post;
import com.dmoffat.website.model.User;
import com.dmoffat.website.model.Views;
import com.dmoffat.website.rest.ApiResponse;
import com.dmoffat.website.rest.impl.AuthenticationApiResponse;
import com.dmoffat.website.rest.impl.ErrorApiResponse;
import com.dmoffat.website.rest.impl.PagedApiResponse;
import com.dmoffat.website.rest.impl.SuccessApiResponse;
import com.dmoffat.website.service.AuthenticationService;
import com.dmoffat.website.service.BlogService;
import com.dmoffat.website.util.BeanUtils;
import com.dmoffat.website.util.WebUtils;
import com.dmoffat.website.view.pagination.Page;
import com.dmoffat.website.view.pagination.PageRequest;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;

/**
 * @author dan
 */
@Controller
public class AdminController {
    private static final String LOGIN_FAILED_ERROR_CODE = "com.dmoffat.website.error_messages.login_failed";
    private static final String UNAUTHORISED_ERROR_CODE = "com.dmoffat.website.error_messages.unauthorised";

    private MessageSource messageSource;
    private AuthenticationService authenticationService;
    private BlogService blogService;

    private SmartValidator validator;

    @Autowired
    public AdminController(MessageSource messageSource, AuthenticationService authenticationService, BlogService blogService, SmartValidator validator) {
        this.messageSource = messageSource;
        this.authenticationService = authenticationService;
        this.blogService = blogService;
        this.validator = validator;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        Cookie cookie = WebUtils.findCookieByName(request, "auth");

        if(cookie == null) {
            return false;
        }

        return authenticationService.isValidToken(cookie.getValue());
    }

    private static ResponseEntity<ApiResponse> validationError(BindingResult result) {
        return new ResponseEntity<>(ErrorApiResponse.fromBindingResult(result), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/management/post/list")
    @JsonView(Views.Summary.class)
    public ResponseEntity<ApiResponse> listPosts(@RequestAttribute(name = "pageRequest") PageRequest pageRequest) {
        Page<Post> posts = blogService.findAllPosts(pageRequest);

        return new ResponseEntity<>(new PagedApiResponse(posts), HttpStatus.OK);
    }

    @PostMapping("/management/post/new")
    public ResponseEntity<ApiResponse> handleNewPost(@RequestBody @Valid Post newPost, BindingResult result) {

        if(result.hasErrors()) {
            return validationError(result);
        }

        blogService.save(newPost);

        return new ResponseEntity<>(new SuccessApiResponse.Builder().addPayload("post", newPost).build(), HttpStatus.OK);
    }

    @GetMapping("/management/post/{id}/")
    public ResponseEntity<ApiResponse> postDetail(@PathVariable("id") Long id) {
        Post post = blogService.findPostById(id);

        if(post == null) {
            return new ResponseEntity<>(new ErrorApiResponse("111", "A post with that ID doesn't exist."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new SuccessApiResponse.Builder().addPayload("post", post).build(), HttpStatus.OK);
    }

    @PostMapping("/management/post/{id}/edit")
    public ResponseEntity<ApiResponse> handleEditPost(@RequestBody Post editedPost, @PathVariable(name = "id") Long postId) {

        Post post = blogService.findPostById(postId);

        if(post == null) {
            return new ResponseEntity<>(new ErrorApiResponse("111", "A post with that ID doesn't exist."), HttpStatus.BAD_REQUEST);
        }

        // todo: find a cleaner way to achieve this
        // Keep hold of the original content before it gets overwritten
        String originalContent = post.getContent();

        // Allow partial updating
        BeanUtils.copyPropertiesIgnoringNull(editedPost, post);

        BindingResult result = validate(post);

        if(result.hasErrors()) {
            return validationError(result);
        }

        // Finally, update the post
        blogService.update(post, originalContent);

        return new ResponseEntity<>(new SuccessApiResponse.Builder().build(), HttpStatus.OK);
    }

    @PostMapping("/management/post/{id}/delete")
    public ResponseEntity<ApiResponse> handleDeletePost(@PathVariable(name = "id") Long postId) {

        if(blogService.archive(postId)) {
            return new ResponseEntity<>(new SuccessApiResponse.Builder().build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ErrorApiResponse("100", "A post with that ID doesn't exist"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/management/auth")
    public String login(Model model, HttpServletRequest request) {
        if(isAuthenticated(request)) {
            return "redirect:/management/";
        }

        model.addAttribute("user", new User());

        return "login";
    }

    @PostMapping(value = "/management/auth", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ApiResponse> restHandleLogin(@RequestBody @Valid User user, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(ErrorApiResponse.fromBindingResult(result), HttpStatus.BAD_REQUEST);
        }

        if(authenticationService.login(user)) {
            return new ResponseEntity<>(new AuthenticationApiResponse(authenticationService.createTokenForUser(user)), HttpStatus.OK);
        }

        return new ResponseEntity<>(new ErrorApiResponse("100", messageSource.getMessage(LOGIN_FAILED_ERROR_CODE, null, Locale.ENGLISH)), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/management/auth/error")
    public ResponseEntity<ApiResponse> restError() {
        return new ResponseEntity<>(new ErrorApiResponse("101", messageSource.getMessage(UNAUTHORISED_ERROR_CODE, null, Locale.ENGLISH)), HttpStatus.FORBIDDEN);
    }

    @PostMapping("/management/auth")
    public String handleLogin(@Valid User user, BindingResult result, HttpServletResponse response, Model model) {
        if(result.hasErrors()) {
            return "login";
        }

        if(!authenticationService.login(user)) {
            result.rejectValue("username", LOGIN_FAILED_ERROR_CODE);
            model.addAttribute("user", user);
            return "login";
        }

        Cookie authCookie = new Cookie("auth", authenticationService.createTokenForUser(user));
        authCookie.setMaxAge(Integer.MAX_VALUE);
        authCookie.setHttpOnly(true);
        authCookie.setPath("/");
        response.addCookie(authCookie);

        return "redirect:/management/";
    }

    @GetMapping("/management/")
    public String home() {
        return "/admin/home";
    }

    private BindingResult validate(Post post) {
        BindingResult result = new BeanPropertyBindingResult(post, "post");
        this.validator.validate(post, result);
        return result;
    }
}
