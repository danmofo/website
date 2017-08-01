package com.dmoffat.website.view.pagination;

import com.dmoffat.website.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Splitter;

import java.util.Iterator;

/**
 * todo: add sorting on arbitrary fields, instead of the ones we know are present.
 *
 * @author dan
 */
public class Sort {
    private static Sort defaultSort = new Sort(Direction.ASCENDING, Field.ID);

    public static Sort defaultSort() {
        return defaultSort;
    }

    public static Sort idDesc() {
        return new Sort(Direction.DESCENDING, Field.ID);
    }
    public static Sort idAsc() { return new Sort(); };

    // Convert a string (from a request parameter) into a Sort object.
    // They come in the format: FIELD_DIRECTION and returns the default sort for anything else
    public static Sort fromString(String sort) {
        if(sort == null) {
            return defaultSort;
        }

        Iterator<String> parts = Splitter.on('_').split(sort).iterator();

        Field field;
        Direction direction;

        try {
            field = Field.valueOf(parts.next());
            direction = Direction.valueOf(parts.next());
        } catch (IllegalArgumentException exception) {
            return defaultSort;
        }

        return new Sort(direction, field);
    }

    private Direction direction;
    private Field field;

    public Sort() {
        this.direction = Direction.ASCENDING;
        this.field = Field.ID;
    }

    public Sort(Direction direction, Field field) {
        this.direction = direction;
        this.field = field;
    }

    public boolean isAscending() {
        return this.direction == Direction.ASCENDING;
    }

    public String getFieldAsColumnName() {
        return this.field.getColumnName();
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

        private String getColumnName() {
            return columnName;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sort sort = (Sort) o;

        if (direction != sort.direction) return false;
        return field == sort.field;
    }

    @Override
    public int hashCode() {
        int result = direction != null ? direction.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sort{");
        sb.append("direction=").append(direction);
        sb.append(", field=").append(field);
        sb.append('}');
        return sb.toString();
    }
}
