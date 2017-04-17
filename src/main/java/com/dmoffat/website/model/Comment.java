package com.dmoffat.website.model;

import javax.persistence.*;

/**
 * Created by danielmoffat on 15/04/2017.
 */

@Entity
@Table(name = "post_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Comment() {
    }

    private Comment(Builder builder) {
        id = builder.id;
        content = builder.content;
        post = builder.post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return id != null ? id.equals(comment.id) : comment.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static final class Builder {
        private Long id;
        private String content;
        private Post post;

        public Builder() {
        }

        public Builder(Comment copy) {
            this.id = copy.id;
            this.content = copy.content;
            this.post = copy.post;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder post(Post val) {
            post = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
