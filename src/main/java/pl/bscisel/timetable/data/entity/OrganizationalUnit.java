package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Check;

import java.util.Set;

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, exclude = {"childUnits", "classGroups"})
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organizational_units",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"parent_unit_id", "name"}),
                @UniqueConstraint(columnNames = {"is_top_level", "name"})
        })
public class OrganizationalUnit extends AbstractEntity implements Cloneable {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name must be between {min} and {max} characters long")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Nullable
    @Size(max = 1000, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 1000)
    private String description;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent_unit_id")
    private OrganizationalUnit parentUnit;

    /**
     * Whether the organizational unit is at the highest level. This means that this unit is not a child of any organizational unit and has no parent.
     * <p>
     * Needed to create a unique key on the names of the top level organizational units.
     */
    @Setter(AccessLevel.NONE)
    @Nullable
    @Column(name = "is_top_level", nullable = true)
    @Check(constraints = "(is_top_level IS NOT NULL AND is_top_level = 1 AND parent_unit_id IS NULL) or (is_top_level IS NULL AND parent_unit_id IS NOT NULL)")
    private Byte isTopLevel;

    @OneToMany(mappedBy = "parentUnit", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<OrganizationalUnit> childUnits;

    @OneToMany(mappedBy = "organizationalUnit", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ClassGroup> classGroups;

    @PrePersist
    @PreUpdate
    private void updateIsTopLevelByte() {
        if (parentUnit == null) {
            isTopLevel = 1;
        } else {
            isTopLevel = null;
        }
    }

    @Override
    public OrganizationalUnit clone() {
        try {
            return (OrganizationalUnit) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public void setName(String name) {
        this.name = name.strip();
    }
}
