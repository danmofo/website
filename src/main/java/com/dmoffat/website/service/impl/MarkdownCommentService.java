package com.dmoffat.website.service.impl;

import com.dmoffat.website.service.CommentService;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

/**
 * @author dan
 */
public class MarkdownCommentService implements CommentService {
    private Parser parser;
    private HtmlRenderer renderer;

    public MarkdownCommentService(Parser parser, HtmlRenderer renderer) {
        this.parser = parser;
        this.renderer = renderer;
    }

    @Override
    public String render(String comment) {
        Node result = parser.parse(comment);
        return renderer.render(result);
    }
}
