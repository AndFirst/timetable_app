package pl.bscisel.timetable.form;

import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.service.AccountService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountFormTest {

    AccountForm form;
    AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountService = mock(AccountService.class);
        form = spy(AccountForm.class);
        form.setAccountService(accountService);
    }

    @Test
    public void testInit() {
        form.init();
        verify(form, times(1)).setMode(AccountForm.Mode.ADD);
    }


    @Test
    public void testConfigureFields() {
        form.configureFields();
        assertEquals(Autocomplete.OFF, form.emailAddress.getAutocomplete());
        assertEquals(Autocomplete.OFF, form.password.getAutocomplete());
    }

    @Test
    public void testSetMode() {
        form.setMode(AccountForm.Mode.ADD);
        assertEquals("Password", form.password.getLabel());
        assertTrue(form.password.isRequired());
        assertEquals(AccountForm.Mode.ADD, form.mode);

        form.setMode(AccountForm.Mode.EDIT);
        assertEquals("Set new password", form.password.getLabel());
        assertFalse(form.password.isRequired());
        assertEquals(AccountForm.Mode.EDIT, form.mode);
    }

    @Test
    public void testSetFieldsRequired() {
        form.setFieldsRequired();
        assertTrue(form.emailAddress.isRequired());
    }

    @Test
    public void testPopulateFields() {
        mockAccountService();
        form.populateFields();
        assertEquals(2, form.roles.getDataProvider().size(new Query<>()));
        verify(accountService, times(1)).findAllRoles();
    }

    private void mockAccountService() {
        Role admin = new Role();
        admin.setName("ROLE_ADMIN");

        Role user = new Role();
        user.setName("ROLE_USER");
        List<Role> roles = List.of(admin, user);

        when(accountService.findAllRoles()).thenReturn(roles);
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.emailAddress)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.password)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.roles)));
    }

    @Test
    public void testConfigureEnterShortcut() {
        form.configureEnterShortcut();
        verify(form, times(1)).configureEnterShortcutWithFix();
    }

    @Test
    public void testAddComponentsToForm() {
        form.addComponentsToForm();
        assertEquals(4, form.getChildren().count());
    }
}