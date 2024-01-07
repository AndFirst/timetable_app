package pl.bscisel.timetable.form;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.service.TeacherInfoService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultationFormTest {

    ConsultationForm form;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(ConsultationForm.class);
        form.setTeacherInfoService(teacherInfoService);
    }

    @Test
    public void testPopulateFields() {
        form.populateFields();
        verify(teacherInfoService, times(1)).findAll();
        // todo: test with mocked data
    }

    @Test
    public void testSetFieldsRequired() {
        form.setFieldsRequired();
        assertTrue(form.teacher.isRequired());
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.teacher)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.location)));
        assertTrue(form.getBinder().getFields().anyMatch(field -> field.equals(form.description)));
    }

    @Test
    public void testTeacherBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        TeacherInfo teacher = new TeacherInfo();
        teacher.setName("Test teacher");

        TeacherInfo teacher2 = new TeacherInfo();
        teacher2.setName("Test teacher 2");
        form.teacher.setItems(List.of(teacher, teacher2));

        assertNull(bean.getTeacher());
        form.teacher.setValue(teacher2);
        assertNotNull(bean.getTeacher());
        assertEquals("Test teacher 2", bean.getTeacher().getName());
    }

    @Test
    public void testDayOfWeekBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, bean.getDayOfWeek());
    }

    @Test
    public void testStartTimeBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.startTime.setValue(LocalTime.of(8, 0));
        assertEquals(LocalTime.of(8, 0), bean.getStartTime());
    }

    @Test
    public void testEndTimeBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.endTime.setValue(LocalTime.of(9, 30));
        assertEquals(LocalTime.of(9, 30), bean.getEndTime());
    }

    @Test
    public void testLocationBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.location.setValue("Test location");
        assertEquals("Test location", bean.getLocation());
    }

    @Test
    public void testDescriptionBinding() {
        form.setBindings();
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }

    @Test
    public void testConfigureEnterShortcut() {
        form.configureEnterShortcut();
        verify(form, times(1)).configureEnterShortcutWithFix(form.description);
    }

    @Test
    public void testAddComponentsToForm() {
        form.addComponentsToForm();
        assertEquals(5, form.getChildren().count());
    }

    @Test
    public void testSetFormBean() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        assertEquals(bean, form.getFormBean());
        assertTrue(form.teacher.isEnabled());

        TeacherInfo teacher = new TeacherInfo();
        teacher.setName("Test teacher");

        Consultation bean2 = new Consultation();
        bean2.setTeacher(teacher);
        form.setFormBean(bean2);
        assertEquals(bean2, form.getFormBean());
        assertFalse(form.teacher.isEnabled());
    }

}