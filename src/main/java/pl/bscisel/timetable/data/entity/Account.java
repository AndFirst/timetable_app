package pl.bscisel.timetable.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import pl.bscisel.timetable.security.SecurityConfiguration;

import java.util.Set;
import java.util.StringJoiner;

@EqualsAndHashCode(callSuper = false, exclude = {"roles"})
@Data
@Entity
@Table(name = "accounts")
public class Account extends AbstractEntity {

    @NotBlank(message = "Email address cannot be empty")
    @Email(message = "Email address must be a valid email")
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @NotNull(message = "You need to provide a password")
    @Size(min = 8, max = 128, message = "Password must be between {min} and {max} characters long")
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles", joinColumns = {@JoinColumn(name = "account_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.strip().toLowerCase();
    }

    public void setPassword(String password) {
        if (StringUtils.isNotBlank(password))
            this.password = SecurityConfiguration.passwordEncoder().encode(password);
    }

    @Transient
    public String formatRoles() {
        if (roles == null)
            return "";
        StringJoiner sj = new StringJoiner(", ");
        roles.forEach(role -> sj.add(role.getName()));
        return sj.toString();
    }
}
