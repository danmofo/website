package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A base entity
 *
 * todo: remove the JsonView annotation, this stinks.
 *
 * @author dan
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"updated"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity {

    // For some strange reason that I don't quite understand - integration tests fail when
    // trying to persist an entity that extends this class, probably because of the visibility on
    // this field. Originally it was correctly set to "protected", but this caused all integration
    // tests to fail with a "detached entity passed to persist" exception.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // todo: address this JsonView issue, look at an object mapper instead
    @JsonView(Views.Summary.class)
    public Long id;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    @JsonView(Views.Summary.class)
    protected LocalDateTime created;

    @JsonFormat(pattern = "dd-MMM-yyyy HH:mm:ss")
    protected LocalDateTime updated;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        if(this.created == null) {
            this.created = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
