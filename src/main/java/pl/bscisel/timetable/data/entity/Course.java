package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = false, exclude = {"classes"})
@Data
@Entity
@Table(name = "courses")
public class Course extends AbstractEntity {

    @NotBlank(message = "Code cannot be empty")
    @Size(min = 2, max = 50, message = "Code must be between {min} and {max} characters long")
    @Column(name = "code", length = 50, unique = true, nullable = false)
    private String code;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 100, message = "Name must be between {min} and {max} characters long")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Nullable
    @Size(max = 500, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Class> classes;
}
