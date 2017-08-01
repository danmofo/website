package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonView;
import name.fraser.neil.plaintext.diff_match_patch;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @NotEmpty
    @JsonView(Views.Summary.class)
    private String title;

    // todo: validate this properly
    @NotEmpty
    @JsonView(Views.Summary.class)
    private String permalink;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "author_id")
    @JsonView(Views.Summary.class)
    private Author author;

    @NotEmpty
    @JsonView(Views.Summary.class)
    private String content;

    @Column(name = "html_content")
    @JsonView(Views.Summary.class)
    private String htmlContent;

    @JsonView(Views.Summary.class)
    private boolean published = false;

    @Column(name = "posted_on")
    @JsonView(Views.Summary.class)
    private LocalDateTime posted;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonView(Views.Detailed.class)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "post")
    @JsonView(Views.Detailed.class)
    private Set<Comment> comments;

    @JsonView(Views.Summary.class)
    private Boolean archived = false;

    @OneToMany(mappedBy = "post")
    @JsonView(Views.Detailed.class)
    private List<Patch> diffs;

    @Column(name = "original_content")
    @JsonView(Views.Summary.class)
    private String originalContent;

    public int revisionCount() {
        return diffs == null ? 0 : diffs.size() + 1;
    }

    public String version() {
        return version(1);
    }

    public String version(int version) {
        if(version == 1) {
            return originalContent;
        }

        if(version > diffs.size() + 1) {
            throw new IllegalArgumentException("Wanted version:" + version + ", but there are only " + (diffs.size() + 1) + " versions available.");
        }

        // Apply the diffs until we reach the required revision
        diff_match_patch diffMatchPatch = new diff_match_patch();

        String result = getOriginalContent();

        for (int i = 0; i < diffs.size(); i++) {
            LinkedList<diff_match_patch.Patch> p = (LinkedList)diffMatchPatch.patch_fromText(diffs.get(i).getText());
            result = (String)diffMatchPatch.patch_apply(p, result)[0];
            if(i + 2 == version) {
                return result;
            }
        }

        return result;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public Post() {
        this.diffs = new ArrayList<>();
        this.tags = new HashSet<>();
        this.comments = new HashSet<>();
    }

    public Boolean getArchived() {
        return archived;
    }

    public List<Patch> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Patch> diffs) {
        this.diffs = diffs;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreated() {
        return super.getCreated();
    }

    public LocalDateTime getUpdated() {
        return super.getUpdated();
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
        setComments(builder.comments);
        setArchived(builder.archived);
        setDiffs(builder.diffs);
        setCreated(builder.created);
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
            this.comments = new HashSet<>();
        }

        this.comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comment.setPost(null);
        this.comments.remove(comment);
    }

    public void addDiff(Patch diff) {
        if(this.diffs == null) {
            this.diffs = new ArrayList<>();
        }

        diff.setPost(this);
        this.diffs.add(diff);
    }

    public static final class Builder {
        private Long id;
        private LocalDateTime created;
        private String title;
        private LocalDateTime updated;
        private String permalink;
        private Author author;
        private String content;
        private String originalContent;
        private String htmlContent;
        private boolean published;
        private LocalDateTime posted;
        private Set<Tag> tags;
        private Set<Comment> comments;
        private Boolean archived;
        private List<Patch> diffs;

        public Builder() {
            published = false;
            tags = new HashSet<>();
            comments = new HashSet<>();
            diffs = new ArrayList<>();
        }

        public Builder(Post copy) {
            this.title = copy.title;
            this.permalink = copy.permalink;
            this.author = copy.author;
            this.content = copy.content;
            this.content = copy.originalContent;
            this.htmlContent = copy.htmlContent;
            this.published = copy.published;
            this.posted = copy.posted;
            this.tags = copy.tags;
            this.comments = copy.comments;
            this.archived = copy.archived;
            this.diffs = copy.diffs;
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

        public Builder author(Author val) {
            author = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder originalContent(String val) {
            originalContent = val;
            return this;
        }

        public Builder diffs(List<Patch> val) {
            diffs = val;
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

        public Builder comments(Set<Comment> val) {
            comments = val;
            return this;
        }

        public Builder archived(boolean val) {
            archived = val;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Post{");
        sb.append("title='").append(title).append('\'');
        sb.append(", permalink='").append(permalink).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", htmlContent='").append(htmlContent).append('\'');
        sb.append(", published=").append(published);
        sb.append(", posted=").append(posted);
        sb.append(", archived=").append(archived);
        sb.append('}');
        return sb.toString();
    }
}
