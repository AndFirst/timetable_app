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

    /**
     * Retrieves the authenticated Spring user details from the current security context.
     *
     * @return A UserDetailsExt object representing the authenticated user, or null if no user is authenticated.
     */
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

    /**
     * Get Account entity of authenticated user.
     * @return account or null if user is not authenticated
     */
    @Nullable
    public Account getAuthenticatedTimetableAccount() {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (authenticatedUser == null) return null;

        return authenticatedUser.getTimetableAccount();
    }

    /**
     * Get Account entity of teacher. User has to be authenticated and have teacher role.
     * @return account or null if user is not authenticated or has no teacher role
     */
    @Nullable
    public Account getTimetableAccountOfAuthenticatedTeacher() {
        return getTimetableAccountOfRole(ROLE_TEACHER);
    }

    /**
     * Get Account entity of admin. User has to be authenticated and have admin role.
     * @return account or null if user is not authenticated or has no admin role
     */
    @Nullable
    public Account getTimetableAccountOfAuthenticatedAdmin() {
        return getTimetableAccountOfRole(ROLE_ADMIN);
    }

    /**
     * Get Account entity of authenticated user if user has role.
     * @param roleAdmin role name
     * @return Account entity if user has role, null otherwise
     */
    @Nullable
    private Account getTimetableAccountOfRole(@NotNull String roleAdmin) {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (!hasUserRole(authenticatedUser, roleAdmin))
            return null;

        assert authenticatedUser != null;
        return authenticatedUser.getTimetableAccount();
    }

    /**
     * Check if user has role.
     * @param authenticatedUser authenticated user
     * @param roleName role name
     * @return true if user has role, false otherwise
     */
    public boolean hasUserRole(@Nullable UserDetailsExt authenticatedUser, @NotNull String roleName) {
        if (authenticatedUser == null) return false;

        return authenticatedUser.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(roleName));
    }

    /**
     * Check if user is admin.
     * @return true if user is admin, false otherwise
     */
    public boolean isUserAdmin() {
        return hasUserRole(getAuthenticatedSpringUser(), ROLE_ADMIN);
    }

    /**
     * Check if user is teacher.
     * @return true if user is teacher, false otherwise
     */
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
