package pl.bscisel.timetable.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.service.CourseService;

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
    public void testConfigureForm() {
        form.configureForm();
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
    }

    @Test
    public void testSetRequiredFields() {
        form.setFieldsRequired();
        assertTrue(form.code.isRequired());
        assertTrue(form.name.isRequired());
    }

    @Test
    public void testPopulateFields() {
        form.populateFields();
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.code)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.name)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.description)));
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

    @Test
    public void testConfigureEnterShortcut() {
        form.configureEnterShortcut();
        verify(form).configureEnterShortcutWithFix(form.description);
    }

    @Test
    public void testAddComponentsToForm() {
        form.addComponentsToForm();
        assertEquals(4, form.getChildren().count());
    }
}