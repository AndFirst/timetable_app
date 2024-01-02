package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.bscisel.timetable.security.SecurityConfiguration;

import java.util.Set;

@EqualsAndHashCode(callSuper = false, exclude = {"roles"})
@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "Email address must be a valid email")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @NotNull(message = "You need to provide a password")
    @Size(min = 8, max = 128, message = "Password must be between {min} and {max} characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public void setPassword(String password) {
        if (password != null && !password.isEmpty())
            this.password = SecurityConfiguration.passwordEncoder().encode(password);
    }

    @Transient
    public String formatRoles() {
        StringBuilder sb = new StringBuilder();
        for (Role role : roles) {
            sb.append(role.getName()).append(", ");
        }
        if (!sb.isEmpty())
            sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
