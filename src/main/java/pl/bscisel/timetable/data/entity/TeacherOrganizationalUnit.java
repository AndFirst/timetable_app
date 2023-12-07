package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class TeacherOrganizationalUnit extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed {max} characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed {max} characters")
    private String description;

    @ManyToOne
    private TeacherOrganizationalUnit parentUnit;

    @Nullable
    @OneToMany(mappedBy = "parentUnit")
    private Set<TeacherOrganizationalUnit> childUnits;

    @Nullable
    @OneToMany(mappedBy = "organizationalUnit")
    private Set<Teacher> teachers;
}
