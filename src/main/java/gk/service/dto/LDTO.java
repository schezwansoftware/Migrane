package gk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the L entity.
 */
public class LDTO implements Serializable {

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

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getBreadth() {
        return breadth;
    }

    public void setBreadth(Integer breadth) {
        this.breadth = breadth;
    }

    public Double getArea() {
        return area;
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

        LDTO lDTO = (LDTO) o;
        if(lDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LDTO{" +
            "id=" + getId() +
            ", length='" + getLength() + "'" +
            ", breadth='" + getBreadth() + "'" +
            ", area='" + getArea() + "'" +
            "}";
    }
}
