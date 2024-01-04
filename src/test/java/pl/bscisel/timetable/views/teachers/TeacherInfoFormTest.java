package pl.bscisel.timetable.views.teachers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.data.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class TeacherInfoFormTest {

    TeacherInfoForm form;
    UserService userService;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(new TeacherInfoForm(userService, teacherInfoService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.name.isRequired());
        assertTrue(form.surname.isRequired());
    }

    @Test
    public void testNameBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        form.name.setValue("Test name");
        assertEquals("Test name", bean.getName());
    }

    @Test
    public void testSurnameBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        form.surname.setValue("Test surname");
        assertEquals("Test surname", bean.getSurname());
    }

    @Test
    public void testDegreeBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        form.degree.setValue("Test degree");
        assertEquals("Test degree", bean.getDegree());
    }

    @Test
    public void testPhoneNumberBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        form.phoneNumber.setValue("123456788");
        assertEquals("123456788", bean.getPhoneNumber());
    }

    @Test
    public void testBiographyBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        form.biography.setValue("Test biography");
        assertEquals("Test biography", bean.getBiography());
    }

    @Test
    public void testUserBinding() {
        TeacherInfo bean = new TeacherInfo();
        form.setFormBean(bean);
        User user = new User();
        user.setId(1L);
        form.user.setValue(user);
        assertEquals(user.getId(), bean.getUser().getId());
    }

}