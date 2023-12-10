package pl.bscisel.timetable.security;

import org.springframework.security.core.userdetails.UserDetails;
import pl.bscisel.timetable.data.entity.User;

public interface UserDetailsExt extends UserDetails {
    User getTimetableUser();
}
