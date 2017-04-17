package com.dmoffat.website.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * A base entity
 *
 * @author dan
 */
public class BaseEntity {

    private LocalDateTime created;
    private LocalDateTime updated;

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updated = LocalDateTime.now();
    }
}
