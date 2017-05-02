package com.dmoffat.website.service;

import com.dmoffat.website.model.Comment;
import com.dmoffat.website.service.impl.MarkdownCommentService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * These tests test the CommentService functionality. I am assuming the libraries I'm using (Jsoup + flexmark) are implemented correctly, but will add
 * tests to cover various types of input eventually.
 *
 * @author dan
 */
public class CommentServiceTests {
    private Comment basicComment;
    private Comment commentWithAllowedHtml;
    private Comment commentWithMaliciousHtml;
    private Comment markdownComment;

    private CommentService commentService;

    @Before
    public void setUp() throws Exception {
        this.commentService = new MarkdownCommentService(Parser.builder().build(), HtmlRenderer.builder().build());
        this.basicComment = new Comment.Builder().name("Daniel Moffat").content("This is a great website.").build();
        this.commentWithAllowedHtml = new Comment.Builder().name("Daniel Moffat").content("<strong>Basic comment</strong>").build();
        this.commentWithMaliciousHtml = new Comment.Builder().name("<video>Daniel Moffat</video>").content("<script>alert('Hello world!')</script>Test").build();
        this.markdownComment = new Comment.Builder().name("Anonymous").content("# This is a title").build();
    }

    // Test the clean function on multiple inputs and make sure the malicious content is removed.
    @Test
    public void testCleaningComment() throws Exception {
        assertEquals(basicComment.getContent(), commentService.clean(basicComment.getContent()));
        assertEquals(commentWithAllowedHtml.getContent(), commentService.clean(commentWithAllowedHtml.getContent()));
        assertEquals(markdownComment.getContent(), commentService.clean(markdownComment.getContent()));
        assertEquals("Test", commentService.clean(commentWithMaliciousHtml.getContent()));

        assertEquals(basicComment, commentService.clean(basicComment));
        assertEquals(commentWithAllowedHtml, commentService.clean(commentWithAllowedHtml));
        assertEquals(markdownComment, commentService.clean(markdownComment));
        assertEquals(new Comment.Builder().name("Daniel Moffat").content("Test").build(), commentService.clean(commentWithMaliciousHtml));
    }

    // Test the render function can render markdown to HTML
    @Test
    public void testRenderingComment() throws Exception {
        assertEquals("<h1>This is a title</h1>\n", commentService.render(markdownComment.getContent()));
    }
}
