package com.dmoffat.website.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @NotEmpty
    private String title;

    // todo: validate this properly
    @NotEmpty
    private String permalink;

    @NotEmpty
    private String author;

    @NotEmpty
    private String content;

    @Column(name = "html_content")
    private String htmlContent;

    private boolean published = false;

    @Column(name = "posted_on")
    private LocalDateTime posted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
    private List<Comment> comments;

    public Post() {
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public LocalDateTime getPosted() {
        return posted;
    }

    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
        setTitle(builder.title);
        setPermalink(builder.permalink);
        setAuthor(builder.author);
        setContent(builder.content);
        setHtmlContent(builder.htmlContent);
        setPublished(builder.published);
        setPosted(builder.posted);
        setTags(builder.tags);
        setId(builder.id);
        setCreated(builder.created);
        setUpdated(builder.updated);
        setComments(builder.comments);
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
        private LocalDateTime created;
        private String title;
        private LocalDateTime updated;
        private String permalink;
        private String author;
        private String content;
        private String htmlContent;
        private boolean published;
        private LocalDateTime posted;
        private Set<Tag> tags;
        private List<Comment> comments;

        public Builder() {
            published = false;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder created(LocalDateTime val) {
            created = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder updated(LocalDateTime val) {
            updated = val;
            return this;
        }

        public Builder permalink(String val) {
            permalink = val;
            return this;
        }

        public Builder author(String val) {
            author = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder htmlContent(String val) {
            htmlContent = val;
            return this;
        }

        public Builder published(boolean val) {
            published = val;
            return this;
        }

        public Builder posted(LocalDateTime val) {
            posted = val;
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


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("title='").append(title).append('\'');
        sb.append(", permalink='").append(permalink).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", htmlContent='").append(htmlContent).append('\'');
        sb.append(", published=").append(published);
        sb.append(", posted=").append(posted);
        sb.append(", comments=").append(comments);
        sb.append('}');
        return sb.toString();
    }
}
