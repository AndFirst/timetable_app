package pl.bscisel.timetable.security;

import org.springframework.security.core.GrantedAuthority;
import pl.bscisel.timetable.data.entity.Account;

import java.util.Collection;

public class UserExt extends org.springframework.security.core.userdetails.User implements UserDetailsExt {

    private final Account account;

    public UserExt(String username, String password, Collection<? extends GrantedAuthority> authorities, Account account) {
        super(username, password, authorities);
        this.account = account;
    }

    @Override
    public Account getTimetableAccount() {
        return account;
    }
}
