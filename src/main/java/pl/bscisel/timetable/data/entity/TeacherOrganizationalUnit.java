package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Check;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "teacher_organizational_units",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"parent_unit_id", "name"}),
                @UniqueConstraint(columnNames = {"is_top_level", "name"})
        })
public class TeacherOrganizationalUnit extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed {max} characters")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Nullable
    @Size(max = 1000, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 1000)
    private String description;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_unit_id")
    private TeacherOrganizationalUnit parentUnit;

    /**
     * Whether the organizational unit is at the highest level. This means that this unit is not a child of any organizational unit and has no parent.
     * <p>
     * Needed to create a unique key on the names of the top level organizational units.
     */
    @Nullable
    @Column(name = "is_top_level", nullable = true)
    @Check(constraints = "(is_top_level IS NOT NULL AND is_top_level = 1 AND parent_unit_id IS NULL) or (is_top_level IS NULL AND parent_unit_id IS NOT NULL)")
    private Byte isTopLevel;

    @Nullable
    @OneToMany(mappedBy = "parentUnit")
    private Set<TeacherOrganizationalUnit> childUnits;

    @Nullable
    @OneToMany(mappedBy = "teacherOrganizationalUnit")
    private Set<TeacherInfo> teachers;
}
