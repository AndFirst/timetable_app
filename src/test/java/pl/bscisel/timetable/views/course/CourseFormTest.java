package pl.bscisel.timetable.views.course;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.views.course.CourseForm;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseFormTest {

    CourseService courseService;
    CourseForm form;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        form = spy(CourseForm.class);
        form.setCourseService(courseService);
    }

    @Test
    public void testInit() {
        form.init();
        verify(form).setRequiredFields();
        verify(form).setBindings();
        verify(form).configureEnterShortcut(form.description);

        assertEquals(4, form.getChildren().count()); // 3 fields + buttons
    }

    @Test
    public void testSetRequiredFields() {
        form.setRequiredFields();
        assertTrue(form.code.isRequired());
        assertTrue(form.name.isRequired());
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertNotNull(form.getBinder().getBinding("code"));
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testCodeBinding() {
        form.setBindings();
        Course bean = new Course();
        form.setFormBean(bean);
        form.code.setValue("TEST");
        assertEquals("TEST", bean.getCode());
    }

    @Test
    public void testNameBinding() {
        form.setBindings();
        Course bean = new Course();
        form.setFormBean(bean);
        form.name.setValue("Test course");
        assertEquals("Test course", bean.getName());
    }

    @Test
    public void testDescriptionBinding() {
        form.setBindings();
        Course bean = new Course();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }
}