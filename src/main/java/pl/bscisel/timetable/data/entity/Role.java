package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 6, max = 20, message = "Name must be between {min} and {max} characters long")
    @Column(name = "name", unique = true, length = 20)
    private String name;

    @PreUpdate
    @PrePersist
    void checkIfStartsWithRole() {
        if (!name.startsWith("ROLE_")) {
            throw new IllegalArgumentException("Role name must start with ROLE_");
        }
    }

    /**
     * Sets the name of the role. The name is stripped of whitespaces.
     * @param name the name of the role
     */
    public void setName(String name) {
        this.name = name.strip();
    }
}
