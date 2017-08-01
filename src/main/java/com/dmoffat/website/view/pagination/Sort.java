package com.dmoffat.website.view.pagination;

import com.dmoffat.website.model.Views;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * todo: add sorting on arbitrary fields, instead of the ones we know are present.
 *
 * @author dan
 */
public class Sort {

    public static Sort idDesc() {
        return new Sort();
    }

    private Direction direction;
    private Field field;

    public Sort() {
        this.direction = Direction.DESCENDING;
        this.field = Field.ID;
    }

    public Sort(Direction direction, Field field) {
        this.direction = direction;
        this.field = field;
    }

    @JsonView(Views.Summary.class)
    public Direction getDirection() {
        return direction;
    }

    @JsonView(Views.Summary.class)
    public Field getField() {
        return field;
    }

    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    public enum Field {
        ID("id"),
        CREATED("created"),
        UPDATED("updated");

        private String columnName;

        Field(String columnName) {
            this.columnName = columnName;
        }
    }
}
