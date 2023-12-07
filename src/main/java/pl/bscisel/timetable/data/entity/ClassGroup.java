package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class ClassGroup extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 20, message = "Name cannot exceed {max} characters")
    private String name;

    @Size(max = 100, message = "Description cannot exceed {max} characters")
    private String description;

    @ManyToOne
    @NotNull
    private OrganizationalUnit organizationalUnit;

}
