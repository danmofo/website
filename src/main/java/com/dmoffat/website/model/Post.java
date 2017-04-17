package com.dmoffat.website.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    public Post() {
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

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    private Post(Builder builder) {
        id = builder.id;
        content = builder.content;
        tags = builder.tags;
        comments = builder.comments;
    }

    public void addTag(Tag tag) {
        if(this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    public void addComment(Comment comment) {
        if(this.comments == null) {
            this.comments = new ArrayList<>();
        }

        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comment.setPost(null);
        this.comments.remove(comment);
    }

    public static final class Builder {
        private Long id;
        private String content;
        private Set<Tag> tags;
        private List<Comment> comments;

        public Builder() {
        }

        public Builder(Post copy) {
            this.id = copy.id;
            this.content = copy.content;
            this.tags = copy.tags;
            this.comments = copy.comments;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder tags(Set<Tag> val) {
            tags = val;
            return this;
        }

        public Builder comments(List<Comment> val) {
            comments = val;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
