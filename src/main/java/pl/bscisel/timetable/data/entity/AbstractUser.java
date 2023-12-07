package pl.bscisel.timetable.data.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public abstract class AbstractUser extends AbstractEntity {

    @Email(message = "Email address must be a valid email")
    @NotEmpty(message = "Email address cannot be empty")
    private String emailAddress;

    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 3, max = 50, message = "Name must be between {min} and {max} characters long")
    private String name;

    @NotEmpty(message = "Surname cannot be empty")
    @Size(min = 3, max = 50, message = "Surname must be between {min} and {max} characters long")
    private String surname;

}
