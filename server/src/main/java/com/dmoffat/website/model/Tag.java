package com.dmoffat.website.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by danielmoffat on 15/04/2017.
 */
@Entity
@Table(name = "tag")
@JsonIgnoreProperties(value = {"created", "updated", "id"})
public class Tag extends BaseEntity {
    private String value;

    public Tag() {
    }

    private Tag(Builder builder) {
        setId(builder.id);
        setValue(builder.value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return value != null ? value.equals(tag.value) : tag.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public static final class Builder {
        private Long id;
        private String value;

        public Builder() {
        }

        public Builder(Tag copy) {
            this.id = copy.id;
            this.value = copy.value;
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }


        public Builder value(String val) {
            value = val;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
