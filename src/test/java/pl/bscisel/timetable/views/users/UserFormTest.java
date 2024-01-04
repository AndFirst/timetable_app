package pl.bscisel.timetable.views.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.UserService;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserFormTest {

    UserForm form;
    UserService userService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        form = spy(new UserForm(userService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.emailAddress.isRequired());
        assertTrue(form.password.isRequired());
        form.setMode(UserForm.Mode.EDIT);
        assertFalse(form.password.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
        assertNotNull(form.getBinder().getBinding("emailAddress"));
        assertNotNull(form.getBinder().getBinding("password"));
        assertNotNull(form.getBinder().getBinding("roles"));
    }

    @Test
    public void testEmailAddressBinding() {
        User bean = new User();
        form.setFormBean(bean);
        form.emailAddress.setValue("email@email.com");
        assertEquals(form.emailAddress.getValue(), bean.getEmailAddress());
    }

    @Test
    public void testPasswordBinding() {
        User bean = new User();
        form.setFormBean(bean);
        form.password.setValue("password");
        assertEquals(60, bean.getPassword().length());
    }

    @Test
    public void testRolesBinding() {
        User bean = new User();
        form.setFormBean(bean);
        Role role = new Role();
        role.setName("ROLE_TEST");
        form.roles.setValue(new LinkedHashSet<>(List.of(role)));
        assertEquals(1, bean.getRoles().size());
    }

    @Test
    public void testPopulateFields() {
        Role role = new Role();
        role.setName("ROLE_TEST");
        when(userService.findAllRoles()).thenReturn(List.of(role));
        form.populateFields();
        assertEquals(1, form.roles.getListDataView().getItems().toList().size());
    }
}