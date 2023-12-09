package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "class_groups", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "organizational_unit_id"})})
public class ClassGroup extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 50, message = "Name cannot exceed {max} characters")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Nullable
    @Size(max = 500, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "organizational_unit_id", nullable = false)
    private OrganizationalUnit organizationalUnit;

    @Nullable
    @OneToMany(mappedBy = "classGroup")
    private Set<Class> classes;

}
