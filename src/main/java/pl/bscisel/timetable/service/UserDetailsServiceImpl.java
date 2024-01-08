package pl.bscisel.timetable.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.repository.AccountRepository;
import pl.bscisel.timetable.security.UserExt;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing user details.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    /**
     * Constructs an instance of UserDetailsServiceImpl.
     */
    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Loads a user by username.
     *
     * @param username The username of the user to load.
     * @return The user details.
     * @throws UsernameNotFoundException If the user could not be found.
     */
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmailAddressIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new UserExt(username, account.getPassword(), authorities, account);
    }
}