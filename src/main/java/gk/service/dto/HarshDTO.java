package gk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Harsh entity.
 */
public class HarshDTO implements Serializable {

    private UUID id;

    @NotNull
    private String arc;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getArc() {
        return arc;
    }

    public void setArc(String arc) {
        this.arc = arc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HarshDTO harshDTO = (HarshDTO) o;
        if(harshDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), harshDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HarshDTO{" +
            "id=" + getId() +
            ", arc='" + getArc() + "'" +
            "}";
    }
}
