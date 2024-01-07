package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.repository.UserRepository;
import pl.bscisel.timetable.service.UserDetailsServiceImpl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testLoadUserByUsername() {
        String email = "email@email.com";
        User user = new User();
        user.setEmailAddress(email);
        user.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");
        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");

        user.setRoles(Set.of(role, role2));

        when(userRepository.findByEmailAddressIgnoreCase(email)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        assertEquals(email, userDetails.getUsername());
        assertFalse(userDetails.getPassword().isEmpty());
        Collection<?> authorities = userDetails.getAuthorities();
        assertEquals(2, authorities.size());
        assertEquals("ROLE_ADMIN", authorities.stream().findFirst().get().toString()); // it is sorted
    }

}
