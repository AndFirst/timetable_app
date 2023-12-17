package pl.bscisel.timetable.data.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = false, exclude = {"user", "classes", "consultations"})
@Data
@Entity
@Table(name = "teacher_info")
public class TeacherInfo extends AbstractEntity {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters long")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 3, max = 50, message = "Surname must be between {min} and {max} characters long")
    private String surname;

    @Size(max = 20, message = "Degree cannot exceed {max} characters")
    @Column(name = "degree", length = 20)
    private String degree;

    @Size(max = 15, message = "Phone number cannot exceed {max} characters")
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Size(max = 1000, message = "Biography cannot exceed {max} characters")
    @Column(name = "biography", length = 1000)
    private String biography;

    @Nullable
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "teachers")
    private Set<Class> classes;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Consultation> consultations;

    @Transient
    public String getFullName() {
        return degree + " " + name + " " + surname;
    }
}

