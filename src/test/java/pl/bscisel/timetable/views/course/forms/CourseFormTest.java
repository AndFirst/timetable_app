package pl.bscisel.timetable.views.course.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class CourseFormTest {

    CourseService courseService;
    CourseForm form;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        form = spy(new CourseForm(courseService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.code.isRequired());
        assertTrue(form.name.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
        assertNotNull(form.getBinder().getBinding("code"));
        assertNotNull(form.getBinder().getBinding("name"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testCodeBinding() {
        Course bean = new Course();
        form.setFormBean(bean);
        form.code.setValue("TEST");
        assertEquals("TEST", bean.getCode());
    }

    @Test
    public void testNameBinding() {
        Course bean = new Course();
        form.setFormBean(bean);
        form.name.setValue("Test course");
        assertEquals("Test course", bean.getName());
    }

    @Test
    public void testDescriptionBinding() {
        Course bean = new Course();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }
}