package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by danielmoffat on 15/04/2017.
 */

@Entity
@Table(name = "post_comment")
@JsonIgnoreProperties(value = {"id", "post"})
public class Comment extends BaseEntity {
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String name;

    public Comment() {
    }

    private Comment(Builder builder) {
        setId(builder.id);
        setContent(builder.content);
        setPost(builder.post);
        setName(builder.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        return name != null ? name.equals(comment.name) : comment.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static final class Builder {
        private Long id;
        private String content;
        private Post post;
        private String name;

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

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
