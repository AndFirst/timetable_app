package pl.bscisel.timetable.security;

import org.springframework.security.core.GrantedAuthority;
import pl.bscisel.timetable.data.entity.User;

import java.util.Collection;

public class UserExt extends org.springframework.security.core.userdetails.User implements UserDetailsExt {

    private final User user;

    public UserExt(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
        super(username, password, authorities);
        this.user = user;
    }

    @Override
    public User getTimetableUser() {
        return user;
    }
}
