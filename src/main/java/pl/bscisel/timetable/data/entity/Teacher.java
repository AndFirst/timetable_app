package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Teacher extends AbstractUser {

    @Size(max = 20, message = "Degree cannot exceed {max} characters")
    private String degree;

    @Size(max = 15, message = "Phone number cannot exceed {max} characters")
    private String phoneNumber;

    @Size(max = 1000, message = "Biography cannot exceed {max} characters")
    private String biography;

    @ManyToMany(mappedBy = "teachers")
    private Set<Class> classes;

    @NotNull
    @ManyToOne
    private TeacherOrganizationalUnit organizationalUnit;

    @OneToMany(mappedBy = "teacher")
    private Set<Consultation> consultations;
}

