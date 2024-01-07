package pl.bscisel.timetable.form;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.service.AccountService;
import pl.bscisel.timetable.service.TeacherInfoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherInfoFormTest {

    TeacherInfoForm form;
    AccountService accountService;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        accountService = mock(AccountService.class);
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(TeacherInfoForm.class);
        form.setAccountService(accountService);
        form.setTeacherInfoService(teacherInfoService);
    }

    @Test
    public void testConfigureForm() {
        form.configureForm();
//        assertEquals(1, form.getResponsiveSteps().size());
        assertEquals("33.33%", form.getMaxWidth());
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
    }

    @Test
    public void testSetRequiredFields() {
        form.setFieldsRequired();
        assertTrue(form.name.isRequired());
        assertTrue(form.surname.isRequired());
    }

    @Test
    public void testPopulateFields() {
        mockAccountService();
        form.populateFields();
        verify(accountService, times(1)).findAllAccounts();
        assertEquals(2, form.account.getDataProvider().size(new Query<>()));
    }

    private void mockAccountService() {
        Account account = new Account();
        account.setId(1L);
        account.setEmailAddress("email@email.com");

        Account account2 = new Account();
        account2.setId(2L);
        account2.setEmailAddress("email2@email.com");
        when(accountService.findAllAccounts()).thenReturn(List.of(account, account2));
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.name)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.surname)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.degree)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.phoneNumber)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.biography)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.account)));
    }

    @Test
    public void testNameBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        form.name.setValue("Test");
        assertEquals("Test", teacher.getName());
    }

    @Test
    public void testSurnameBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        form.surname.setValue("Test");
        assertEquals("Test", teacher.getSurname());
    }

    @Test
    public void testDegreeBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        form.degree.setValue("Test");
        assertEquals("Test", teacher.getDegree());
    }

    @Test
    public void testPhoneNumberBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        form.phoneNumber.setValue("Test");
        assertEquals("Test", teacher.getPhoneNumber());
    }

    @Test
    public void testBiographyBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        form.biography.setValue("Test");
        assertEquals("Test", teacher.getBiography());
    }

    @Test
    public void testAccountBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        Account account = new Account();
        account.setId(1L);
        account.setEmailAddress("email@email.com");

        Account account2 = new Account();
        account2.setId(2L);
        account2.setEmailAddress("email2@email.com");
        form.account.setItems(List.of(account, account2));
        form.account.setValue(account2);
        assertNotNull(teacher.getAccount());
        assertEquals("email2@email.com", teacher.getAccount().getEmailAddress());
    }

    @Test
    public void testConfigureEnterShortcut() {
        form.configureEnterShortcut();
        verify(form, times(1)).configureEnterShortcutWithFix(form.biography);
    }

    @Test
    public void testAddComponentsToForm() {
        form.addComponentsToForm();
        assertEquals(7, form.getChildren().count());
    }
}