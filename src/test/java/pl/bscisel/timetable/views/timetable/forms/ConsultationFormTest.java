package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;

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
    public void testInit() {
        form.init();
        verify(form).configureFields();
        verify(form).populateFields();
        verify(form).setBindings();
        verify(form).configureEnterShortcut(form.description);
        assertEquals(5, form.getChildren().count());
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
        verify(form, times(1)).setFieldsRequired();

        assertEquals("Day of week", form.dayOfWeek.getLabel());

        assertTrue(form.dayOfWeek.isRequiredIndicatorVisible());

        assertEquals(LocalTime.of(7, 0), form.startTime.getMin());
        assertEquals(LocalTime.of(21, 45), form.startTime.getMax());

        assertEquals(LocalTime.of(7, 15), form.endTime.getMin());
        assertEquals(LocalTime.of(22, 0), form.endTime.getMax());
    }

    @Test
    public void testPopulateFields() {
        form.populateFields();
        assertEquals(7, form.dayOfWeek.getDataProvider().size(new Query<>()));
        verify(teacherInfoService, times(1)).findAll();
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertNotNull(form.getBinder().getBinding("teacher"));
        assertNotNull(form.getBinder().getBinding("dayOfWeek"));
        assertNotNull(form.getBinder().getBinding("startTime"));
        assertNotNull(form.getBinder().getBinding("endTime"));
        assertNotNull(form.getBinder().getBinding("location"));
        assertNotNull(form.getBinder().getBinding("description"));
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
}