package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @NotBlank(message = "Username cannot be empty")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull(message = "You need to provide a password")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

}
