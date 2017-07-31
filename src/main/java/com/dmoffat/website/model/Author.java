package com.dmoffat.website.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author danielmoffat
 */
@Table(name = "author")
@Entity
public class Author extends BaseEntity {

    @NotEmpty
    private String name;

    public Author() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
