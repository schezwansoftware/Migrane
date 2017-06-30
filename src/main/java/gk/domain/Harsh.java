package gk.domain;

import com.datastax.driver.mapping.annotations.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A Harsh.
 */

@Table(name = "harsh")
public class Harsh implements Serializable {

    private static final long serialVersionUID = 1L;

    @PartitionKey
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

    public Harsh arc(String arc) {
        this.arc = arc;
        return this;
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
        Harsh harsh = (Harsh) o;
        if (harsh.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), harsh.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Harsh{" +
            "id=" + getId() +
            ", arc='" + getArc() + "'" +
            "}";
    }
}
