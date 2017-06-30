package gk.domain;

import com.datastax.driver.mapping.annotations.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A L.
 */

@Table(name = "l")
public class L implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
    private UUID id;

    @NotNull
    private Integer length;

    @NotNull
    private Integer breadth;

    private Double area;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getLength() {
        return length;
    }

    public L length(Integer length) {
        this.length = length;
        return this;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getBreadth() {
        return breadth;
    }

    public L breadth(Integer breadth) {
        this.breadth = breadth;
        return this;
    }

    public void setBreadth(Integer breadth) {
        this.breadth = breadth;
    }

    public Double getArea() {
        return area;
    }

    public L area(Double area) {
        this.area = area;
        return this;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        L l = (L) o;
        if (l.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), l.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "L{" +
            "id=" + getId() +
            ", length='" + getLength() + "'" +
            ", breadth='" + getBreadth() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }
}
