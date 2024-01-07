package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.Account;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class AccountRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;


    @Test
    @Sql(value = "accounts_data.sql")
    void testfindByEmailAddressIgnoreCase() {
        Optional<Account> accounts = accountRepository.findByEmailAddressIgnoreCase("email@EMAIL.com");
        assertTrue(accounts.isPresent());
    }

    @Test
    @Sql(value = "accounts_data.sql")
    void testExistsByEmailAddressIgnoreCase() {
        boolean exists = accountRepository.existsByEmailAddressIgnoreCase("email@EMAIL.com");
        assertTrue(exists);
        exists = accountRepository.existsByEmailAddressIgnoreCase("not@existing.com");
        assertFalse(exists);
    }

    @Test
    @Sql(value = "accounts_data.sql")
    void testExistsByEmailAddressIgnoreCaseAndIdNot() {
        boolean exists = accountRepository.existsByEmailAddressIgnoreCaseAndIdNot("email@EMAIL.com", 1L);
        assertFalse(exists);
        exists = accountRepository.existsByEmailAddressIgnoreCaseAndIdNot("email@EMAIL.com", 2L);
        assertTrue(exists);
    }

    @Test
    @Sql(value = "accounts_data.sql")
    public void testFindByEmailAddressContainsIgnoreCase() {
        List<Account> accounts = accountRepository.findByEmailAddressContainsIgnoreCase("email");
        assertEquals(3, accounts.size());
        accounts = accountRepository.findByEmailAddressContainsIgnoreCase("EMAIL");
        assertEquals(3, accounts.size());
        accounts = accountRepository.findByEmailAddressContainsIgnoreCase("notexisting");
        assertEquals(0, accounts.size());
        accounts = accountRepository.findByEmailAddressContainsIgnoreCase("email2");
        assertEquals(1, accounts.size());
        accounts = accountRepository.findByEmailAddressContainsIgnoreCase("l@");
        assertEquals(1, accounts.size());
    }
}