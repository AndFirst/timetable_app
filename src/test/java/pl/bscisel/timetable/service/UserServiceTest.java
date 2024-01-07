package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.repository.RoleRepository;
import pl.bscisel.timetable.data.repository.UserRepository;
import pl.bscisel.timetable.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void userExistsByEmailAddress() {
        String email = "email@email.com";
        when(userRepository.existsByEmailAddressIgnoreCase(email)).thenReturn(true);
        boolean result = userService.userExistsByEmailAddress(email, null);
        assertTrue(result);
        verify(userRepository, times(1)).existsByEmailAddressIgnoreCase(email);

        when(userRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, 1L)).thenReturn(false);
        result = userService.userExistsByEmailAddress(email, 1L);
        assertFalse(result);
        verify(userRepository, times(1)).existsByEmailAddressIgnoreCaseAndIdNot(email, 1L);
    }

    @Test
    public void testSave() {
        User user = new User();
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDelete() {
        User user = new User();
        userService.delete(user);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testSearch() {
        String filter = "mail@e";
        User user = new User();
        user.setEmailAddress("email@email.com");
        when(userRepository.findByEmailAddressContainsIgnoreCase(filter)).thenReturn(List.of(user));
        List<User> users = userService.search(filter);
        assertEquals(1, users.size());

        String filter2 = " ";
        when(userRepository.findAll()).thenReturn(List.of(user));
        users = userService.search(filter2);
        assertEquals(1, users.size());

        when(userRepository.findAll()).thenReturn(List.of(user));
        users = userService.search(null);
        assertEquals(1, users.size());

        verify(userRepository, times(1)).findByEmailAddressContainsIgnoreCase(filter);
        verify(userRepository, times(2)).findAll();
    }

    @Test
    public void testFindAllRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepository.findAll()).thenReturn(List.of(role));
        List<Role> roles = userService.findAllRoles();
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0).getName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllUsers() {
        User user = new User();
        user.setEmailAddress("email@email.com");
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userService.findAllUsers();
        assertEquals(1, users.size());
        assertEquals("email@email.com", users.get(0).getEmailAddress());
        verify(userRepository, times(1)).findAll();
    }
}
