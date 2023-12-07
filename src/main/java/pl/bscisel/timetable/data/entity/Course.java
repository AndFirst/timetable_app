package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Course extends AbstractEntity {

    @NotBlank
    @Column(name = "code", unique = true)
    private String code;

    @NotEmpty
    @Size(min = 3, max = 100, message = "Name must be between {min} and {max} characters long")
    private String name;

    @Size(max = 500, message = "Description cannot exceed {max} characters")
    private String description;

    @Nullable
    @OneToMany(mappedBy = "course")
    private Set<Class> classes;
}
