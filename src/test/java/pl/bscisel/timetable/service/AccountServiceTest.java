package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.repository.AccountRepository;
import pl.bscisel.timetable.data.repository.RoleRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void testAccountExistsByEmailAddress() {
        String email = "email@email.com";
        when(accountRepository.existsByEmailAddressIgnoreCase(email)).thenReturn(true);
        boolean result = accountService.existsByEmailAddress(email, null);
        assertTrue(result);
        verify(accountRepository, times(1)).existsByEmailAddressIgnoreCase(email);

        when(accountRepository.existsByEmailAddressIgnoreCaseAndIdNot(email, 1L)).thenReturn(false);
        result = accountService.existsByEmailAddress(email, 1L);
        assertFalse(result);
        verify(accountRepository, times(1)).existsByEmailAddressIgnoreCaseAndIdNot(email, 1L);
    }

    @Test
    public void testSave() {
        Account account = new Account();
        accountService.save(account);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testDelete() {
        Account account = new Account();
        accountService.delete(account);
        verify(accountRepository, times(1)).delete(account);
    }

    @Test
    public void testSearch() {
        String filter = "mail@e";
        Account account = new Account();
        account.setEmailAddress("email@email.com");
        when(accountRepository.findByEmailAddressContainsIgnoreCase(filter)).thenReturn(List.of(account));
        List<Account> accounts = accountService.search(filter);
        assertEquals(1, accounts.size());

        String filter2 = " ";
        when(accountRepository.findAll()).thenReturn(List.of(account));
        accounts = accountService.search(filter2);
        assertEquals(1, accounts.size());

        when(accountRepository.findAll()).thenReturn(List.of(account));
        accounts = accountService.search(null);
        assertEquals(1, accounts.size());

        verify(accountRepository, times(1)).findByEmailAddressContainsIgnoreCase(filter);
        verify(accountRepository, times(2)).findAll();
    }

    @Test
    public void testFindAllRoles() {
        Role role = new Role();
        role.setName("ROLE_USER");
        when(roleRepository.findAll()).thenReturn(List.of(role));
        List<Role> roles = accountService.findAllRoles();
        assertEquals(1, roles.size());
        assertEquals("ROLE_USER", roles.get(0).getName());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllAccounts() {
        Account account = new Account();
        account.setEmailAddress("email@email.com");
        when(accountRepository.findAll()).thenReturn(List.of(account));
        List<Account> accounts = accountService.findAllAccounts();
        assertEquals(1, accounts.size());
        assertEquals("email@email.com", accounts.get(0).getEmailAddress());
        verify(accountRepository, times(1)).findAll();
    }
}
