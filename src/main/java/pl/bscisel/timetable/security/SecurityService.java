package pl.bscisel.timetable.security;

import com.vaadin.flow.server.VaadinServletRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Account;

/**
 * Security service.
 */
@Service
public class SecurityService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_TEACHER = "ROLE_TEACHER";

    @Nullable
    public UserDetailsExt getAuthenticatedSpringUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return null;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsExt) {
            return (UserDetailsExt) principal;
        }
        return null;
    }

    @Nullable
    public Account getAuthenticatedTimetableAccount() {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (authenticatedUser == null) return null;

        return authenticatedUser.getTimetableAccount();
    }

    @Nullable
    public Account getTimetableAccountOfAuthenticatedTeacher() {
        return getTimetableAccountOfRole(ROLE_TEACHER);
    }

    @Nullable
    public Account getTimetableAccountOfAuthenticatedAdmin() {
        return getTimetableAccountOfRole(ROLE_ADMIN);
    }

    @Nullable
    private Account getTimetableAccountOfRole(@NotNull String roleAdmin) {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (!hasUserRole(authenticatedUser, roleAdmin))
            return null;

        assert authenticatedUser != null;
        return authenticatedUser.getTimetableAccount();
    }

    public boolean hasUserRole(@Nullable UserDetailsExt authenticatedUser, @NotNull String roleName) {
        if (authenticatedUser == null) return false;

        return authenticatedUser.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(roleName));
    }

    public boolean isUserAdmin() {
        return hasUserRole(getAuthenticatedSpringUser(), ROLE_ADMIN);
    }

    public boolean isUserTeacher() {
        return hasUserRole(getAuthenticatedSpringUser(), ROLE_TEACHER);
    }

    /**
     * Check if user is logged in.
     *
     * @return true if user is logged in, false otherwise
     */
    public boolean isUserLoggedIn() {
        return getAuthenticatedSpringUser() != null;
    }

    /**
     * Logout user.
     */
    public void logout() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }
}
