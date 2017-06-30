package gk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the Student entity.
 */
public class StudentDTO implements Serializable {

    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudentDTO studentDTO = (StudentDTO) o;
        if(studentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }
}
