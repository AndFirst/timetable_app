package pl.bscisel.timetable.security;

import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.User;

/**
 * Security service.
 */
@Service
public class SecurityService {
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * Get authenticated spring user.
     * @return authenticated user
     */
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
     * Get authenticated timetable user.
     * @return authenticated user
     */
    public User getAuthenticatedTimetableUser() {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (authenticatedUser == null) return null;

        return authenticatedUser.getTimetableUser();
    }

    /**
     * Check if user has given role.
     * @param roleName role name
     * @return true if user has given role, false otherwise
     */
    public boolean hasUserRole(String roleName) {
        UserDetailsExt authenticatedUser = getAuthenticatedSpringUser();
        if (authenticatedUser == null) return false;

        return authenticatedUser.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(roleName));
    }

    /**
     * Check if user has admin role.
     * @return true if user has admin role, false otherwise
     */
    public boolean isUserAdmin() {
        return hasUserRole(ROLE_ADMIN);
    }

    /**
     * Check if user is logged in.
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
