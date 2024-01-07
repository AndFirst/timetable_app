package pl.bscisel.timetable.data.entity.other;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.bscisel.timetable.data.entity.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UniqueConstraintsTest {

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testOrganizationalUnitNameUniqueConstraintTopLevel() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        entityManager.persist(organizationalUnit);
        OrganizationalUnit organizationalUnit2 = new OrganizationalUnit();
        organizationalUnit2.setName("Organizational Unit 1");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(organizationalUnit2));
    }

    @Test
    public void testOrganizationalUnitNameUniqueConstraintNotTopLevel() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        entityManager.persist(organizationalUnit);
        OrganizationalUnit organizationalUnit2 = new OrganizationalUnit();
        organizationalUnit2.setName("Organizational Unit 1");
        organizationalUnit2.setParentUnit(organizationalUnit);
        entityManager.persist(organizationalUnit2);
        OrganizationalUnit organizationalUnit3 = new OrganizationalUnit();
        organizationalUnit3.setName("Organizational Unit 1");
        organizationalUnit3.setParentUnit(organizationalUnit);
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(organizationalUnit3));
    }

    @Test
    public void testClassGroupNameUniqueConstraint() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        entityManager.persist(organizationalUnit);

        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("Class Group 1");
        classGroup.setOrganizationalUnit(organizationalUnit);
        entityManager.persist(classGroup);

        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setName("Class Group 2");
        classGroup2.setOrganizationalUnit(organizationalUnit);
        entityManager.persist(classGroup2);

        ClassGroup classGroup3 = new ClassGroup();
        classGroup3.setName("Class Group 1");
        classGroup3.setOrganizationalUnit(organizationalUnit);
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(classGroup3));
    }

    @Test
    public void testCourseCodeUniqueConstraint() {
        Course course = new Course();
        course.setCode("ABC123");
        course.setName("Course 1");
        entityManager.persist(course);

        Course course2 = new Course();
        course2.setCode("DEF456");
        course2.setName("Course 2");
        entityManager.persist(course2);

        Course course3 = new Course();
        course3.setCode("ABC123");
        course3.setName("Course 3");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(course3));
    }

    @Test
    public void testRoleNameUniqueConstraint() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        entityManager.persist(role);

        Role role2 = new Role();
        role2.setName("ROLE_USER");
        entityManager.persist(role2);

        Role role3 = new Role();
        role3.setName("ROLE_ADMIN");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(role3));
    }

    @Test
    public void testAccountEmailAddressUniqueConstraint() {
        Account account = new Account();
        account.setEmailAddress("email@email.com");
        account.setPassword("password");
        entityManager.persist(account);

        Account account2 = new Account();
        account2.setEmailAddress("email2@email.com");
        account2.setPassword("password");
        entityManager.persist(account2);

        Account account3 = new Account();
        account3.setEmailAddress("email@email.com");
        account3.setPassword("password");
        assertThrows(ConstraintViolationException.class, () -> entityManager.persist(account3));
    }
}
