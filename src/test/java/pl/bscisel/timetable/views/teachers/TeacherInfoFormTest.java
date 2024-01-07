package pl.bscisel.timetable.views.teachers;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.data.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherInfoFormTest {

    TeacherInfoForm form;
    UserService userService;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(TeacherInfoForm.class);
        form.setUserService(userService);
        form.setTeacherInfoService(teacherInfoService);
    }

    @Test
    public void testInit() {
        form.init();
        verify(form).setRequiredFields();
        verify(form).configureForm();
        verify(form).configureEnterShortcut(form.biography);
        verify(form).setBindings();
        verify(form).populateFields();

        assertEquals(7, form.getChildren().count()); // 6 fields + buttons
    }

    @Test
    public void testSetRequiredFields() {
        form.setRequiredFields();
        assertTrue(form.name.isRequired());
        assertTrue(form.surname.isRequired());
    }

    @Test
    public void testConfigureForm() {
        form.configureForm();
//        assertEquals(1, form.getResponsiveSteps().size());
        assertEquals("33.33%", form.getMaxWidth());
    }

    @Test
    public void testPopulateFields() {
        mockUserService();
        form.populateFields();
        verify(userService, times(1)).findAllUsers();
        assertEquals(2, form.user.getDataProvider().size(new Query<>()));
    }

    private void mockUserService() {
        User user = new User();
        user.setId(1L);
        user.setEmailAddress("email@email.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmailAddress("email2@email.com");
        when(userService.findAllUsers()).thenReturn(List.of(user, user2));
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("surname"));
        assertNotNull(form.getBinder().getBinding("degree"));
        assertNotNull(form.getBinder().getBinding("phoneNumber"));
        assertNotNull(form.getBinder().getBinding("biography"));
        assertNotNull(form.getBinder().getBinding("user"));
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
    public void testUserBinding() {
        form.setBindings();
        TeacherInfo teacher = new TeacherInfo();
        form.setFormBean(teacher);
        User user = new User();
        user.setId(1L);
        user.setEmailAddress("email@email.com");

        User user2 = new User();
        user2.setId(2L);
        user2.setEmailAddress("email2@email.com");
        form.user.setItems(List.of(user, user2));
        form.user.setValue(user2);
        assertNotNull(teacher.getUser());
        assertEquals("email2@email.com", teacher.getUser().getEmailAddress());
    }
}