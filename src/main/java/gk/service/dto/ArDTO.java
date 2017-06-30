package gk.service.dto;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Ar entity.
 */
public class ArDTO implements Serializable {

    private UUID id;

    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArDTO arDTO = (ArDTO) o;
        if(arDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), arDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
