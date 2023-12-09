package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 20, message = "Name cannot exceed {max} characters")
    @Column(name = "name", unique = true, length = 20)
    private String name;

}
