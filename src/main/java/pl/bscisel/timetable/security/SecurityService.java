package pl.bscisel.timetable.security;

import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import pl.bscisel.timetable.data.entity.User;

@Component
public class SecurityService {
    public UserDetailsExt getAuthenticatedSpringUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsExt) {
            return (UserDetailsExt) principal;
        }
        // Anonymous or no authentication.
        return null;
    }

    public User getAuthenticatedTimetableUser() {
        return getAuthenticatedSpringUser().getTimetableUser();
    }

    public void logout() {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null,
                null);
    }
}
