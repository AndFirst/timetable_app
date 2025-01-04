package pl.bscisel.timetable.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

    /**
     * Sets the email address to lowercase and strips it of whitespaces
     *
     * @param emailAddress the email address to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress.strip().toLowerCase();
    }

    /**
     * Sets the password to a hashed version of the provided password
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        if (StringUtils.isNotBlank(password))
            this.password = SecurityConfiguration.passwordEncoder().encode(password);
    }

    /**
     * Formats the roles into a comma-separated string
     *
     * @return the formatted roles
     */
    @Transient
    public String formatRoles() {
        if (roles == null)
            return "";
        StringJoiner sj = new StringJoiner(", ");
        roles.forEach(role -> sj.add(role.getName()));
        return sj.toString();
    }
}
