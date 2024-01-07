package pl.bscisel.timetable.security;

import org.springframework.security.core.userdetails.UserDetails;
import pl.bscisel.timetable.data.entity.Account;

public interface UserDetailsExt extends UserDetails {
    Account getTimetableAccount();
}
