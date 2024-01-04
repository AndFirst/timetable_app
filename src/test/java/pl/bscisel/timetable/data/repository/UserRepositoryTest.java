package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class UserRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;


    @Test
    @Sql(value = "users_data.sql")
    void testfindByEmailAddressIgnoreCase() {
        Optional<User> users = userRepository.findByEmailAddressIgnoreCase("email@EMAIL.com");
        assertTrue(users.isPresent());
    }

    @Test
    @Sql(value = "users_data.sql")
    void testExistsByEmailAddressIgnoreCase() {
        boolean exists = userRepository.existsByEmailAddressIgnoreCase("email@EMAIL.com");
        assertTrue(exists);
        exists = userRepository.existsByEmailAddressIgnoreCase("not@existing.com");
        assertFalse(exists);
    }

    @Test
    @Sql(value = "users_data.sql")
    void testExistsByEmailAddressIgnoreCaseAndIdNot() {
        boolean exists = userRepository.existsByEmailAddressIgnoreCaseAndIdNot("email@EMAIL.com", 1L);
        assertFalse(exists);
        exists = userRepository.existsByEmailAddressIgnoreCaseAndIdNot("email@EMAIL.com", 2L);
        assertTrue(exists);
    }

    @Test
    @Sql(value = "users_data.sql")
    public void testFindByEmailAddressContainsIgnoreCase() {
        List<User> users = userRepository.findByEmailAddressContainsIgnoreCase("email");
        assertEquals(3, users.size());
        users = userRepository.findByEmailAddressContainsIgnoreCase("EMAIL");
        assertEquals(3, users.size());
        users = userRepository.findByEmailAddressContainsIgnoreCase("notexisting");
        assertEquals(0, users.size());
        users = userRepository.findByEmailAddressContainsIgnoreCase("email2");
        assertEquals(1, users.size());
        users = userRepository.findByEmailAddressContainsIgnoreCase("l@");
        assertEquals(1, users.size());
    }
}