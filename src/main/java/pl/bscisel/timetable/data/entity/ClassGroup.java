package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false, exclude = {"classes"})
@Data
@Entity
@Table(name = "class_groups", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "organizational_unit_id"})})
public class ClassGroup extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between {min} and {max} characters long")
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Nullable
    @Size(max = 500, message = "Description cannot exceed {max} characters")
    @Column(name = "description", length = 500)
    private String description;

    @NotNull(message = "You must select organizational unit")
    @ManyToOne
    @JoinColumn(name = "organizational_unit_id", nullable = false)
    private OrganizationalUnit organizationalUnit;

    @OneToMany(mappedBy = "classGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Class> classes;

    /**
     * Sets name of the class group. Trims whitespaces.
     * @param name name of the class group
     */
    public void setName(String name) {
        this.name = name.strip();
    }
}
