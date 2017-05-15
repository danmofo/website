package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A base entity
 *
 * @author dan
 */
@MappedSuperclass
@JsonIgnoreProperties(value = {"updated", "created", "id"})
public class BaseEntity {

    // For some strange reason that I don't quite understand - integration tests fail when
    // trying to persist an entity that extends this class, probably because of the visibility on
    // this field. Originally it was correctly set to "protected", but this caused all integration
    // tests to fail with a "detached entity passed to persist" exception.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    protected LocalDateTime created;
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
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
