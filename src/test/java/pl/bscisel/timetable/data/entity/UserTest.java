package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserTest {

    @Test
    public void ensureEmailIsLowercased() {
        User user = new User();
        user.setEmailAddress("eMaIl@DoMaIN.COm");
        assertEquals("email@domain.com", user.getEmailAddress());
    }

    @Test
    public void ensureEmailIsStrippedOfWhitespaces() {
        User user = new User();
        user.setEmailAddress("  email@domain.com  ");
        assertEquals("email@domain.com", user.getEmailAddress());
    }

    @Test
    public void testPasswordIsHashedWithBCrypt() {
        User user = new User();
        user.setPassword("password");
        assertEquals(60, user.getPassword().length());
        assertEquals("$2a$10$", user.getPassword().substring(0, 7));
    }

    @Test
    public void testEmptyPasswordIsNotHashed() {
        User user = new User();
        user.setPassword("");
        assertNull(user.getPassword());
    }

    @Test
    public void testFormatRoles() {
        User user = new User();
        Role role1 = new Role();
        role1.setName("role1");
        Role role2 = new Role();
        role2.setName("role2");
        assertEquals("", user.formatRoles());
        user.setRoles(new HashSet<>());
        assertEquals("", user.formatRoles());
        user.getRoles().add(role1);
        assertEquals("role1", user.formatRoles());
        user.getRoles().add(role2);
        assertEquals("role1, role2", user.formatRoles());
    }
}