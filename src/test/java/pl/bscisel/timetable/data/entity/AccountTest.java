package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountTest {

    @Test
    public void ensureEmailIsLowercased() {
        Account account = new Account();
        account.setEmailAddress("eMaIl@DoMaIN.COm");
        assertEquals("email@domain.com", account.getEmailAddress());
    }

    @Test
    public void ensureEmailIsStrippedOfWhitespaces() {
        Account account = new Account();
        account.setEmailAddress("  email@domain.com  ");
        assertEquals("email@domain.com", account.getEmailAddress());
    }

    @Test
    public void testPasswordIsHashedWithBCrypt() {
        Account account = new Account();
        account.setPassword("password");
        assertEquals(60, account.getPassword().length());
        assertEquals("$2a$10$", account.getPassword().substring(0, 7));
    }

    @Test
    public void testEmptyPasswordIsNotHashed() {
        Account account = new Account();
        account.setPassword("");
        assertNull(account.getPassword());
    }

    @Test
    public void testFormatRoles() {
        Account account = new Account();
        Role role1 = new Role();
        role1.setName("role1");
        Role role2 = new Role();
        role2.setName("role2");
        assertEquals("", account.formatRoles());
        account.setRoles(new HashSet<>());
        assertEquals("", account.formatRoles());
        account.getRoles().add(role1);
        assertEquals("role1", account.formatRoles());
        account.getRoles().add(role2);
        assertEquals("role1, role2", account.formatRoles());
    }
}