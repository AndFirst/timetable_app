package pl.bscisel.timetable.data.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class RoleTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensureSetNameStripsWhitespaces() {
        Role role = new Role();
        role.setName("  Role  ");
        assertEquals("Role", role.getName());
    }

    @Test
    public void testNameSizeConstraints() {
        Role role = new Role();
        EntityTestUtils.testStringSizeConstraints(role, role::setName, "name", 6, 20);
    }

    @Test
    @Transactional
    public void ensureInvalidRoleNameThrowsException() {
        Role role = new Role();
        role.setName("INVALID_ROLE_NAME");

        assertThrows(IllegalArgumentException.class, () -> entityManager.persist(role));
    }

    @Test
    public void ensureValidRoleIsValid() {
        Role role = new Role();
        role.setName("ROLE_TEACHER");
        assertEquals(0, EntityTestUtils.validator.validate(role).size());
    }
}
