package pl.bscisel.timetable.views.timetable.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class ConsultationFormTest {

    ConsultationForm form;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(new ConsultationForm(teacherInfoService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.teacher.isRequired());
        assertTrue(form.startTime.isRequired());
        assertTrue(form.endTime.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
        assertNotNull(form.getBinder().getBinding("teacher"));
        assertNotNull(form.getBinder().getBinding("dayOfWeek"));
        assertNotNull(form.getBinder().getBinding("startTime"));
        assertNotNull(form.getBinder().getBinding("endTime"));
        assertNotNull(form.getBinder().getBinding("location"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testTeacherBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.teacher.setValue(new TeacherInfo());
        assertNotNull(bean.getTeacher());
    }

    @Test
    public void testDayOfWeekBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, bean.getDayOfWeek());
    }

    @Test
    public void testStartTimeBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.startTime.setValue(LocalTime.of(12, 0));
        assertEquals(LocalTime.of(12, 0), bean.getStartTime());
    }

    @Test
    public void testEndTimeBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.endTime.setValue(LocalTime.of(12, 0));
        assertEquals(LocalTime.of(12, 0), bean.getEndTime());
    }

    @Test
    public void testLocationBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.location.setValue("Test location");
        assertEquals("Test location", bean.getLocation());
    }

    @Test
    public void testDescriptionBinding() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }

    @Test
    public void testTimeValidation() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.teacher.setValue(new TeacherInfo());
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        form.startTime.setValue(LocalTime.of(12, 0));
        form.endTime.setValue(LocalTime.of(11, 0));
        assertFalse(form.getBinder().validate().isOk());
        form.endTime.setValue(LocalTime.of(13, 0));
        assertTrue(form.getBinder().validate().isOk());
    }

    @Test
    public void testTimeLengthValidation() {
        Consultation bean = new Consultation();
        form.setFormBean(bean);
        form.teacher.setValue(new TeacherInfo());
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        form.startTime.setValue(LocalTime.of(12, 0));
        form.endTime.setValue(LocalTime.of(12, 14));
        assertFalse(form.getBinder().validate().isOk());
        form.endTime.setValue(LocalTime.of(12, 15));
        assertTrue(form.getBinder().validate().isOk());
    }

    @Test
    public void testDisableTeacherIfSetEarlier() {
        Consultation bean = new Consultation();
        bean.setTeacher(new TeacherInfo());
        form.setFormBean(bean);
        assertFalse(form.teacher.isEnabled());
    }

}