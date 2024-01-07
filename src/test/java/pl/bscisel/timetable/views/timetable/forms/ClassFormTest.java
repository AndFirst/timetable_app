package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.data.entity.Class;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassFormTest {

    ClassForm form;
    CourseService courseService;
    ClassGroupService classGroupService;
    TeacherInfoService teacherInfoService;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        classGroupService = mock(ClassGroupService.class);
        teacherInfoService = mock(TeacherInfoService.class);
        form = spy(ClassForm.class);
        form.setCourseService(courseService);
        form.setClassGroupService(classGroupService);
        form.setTeacherInfoService(teacherInfoService);
    }

    @Test
    public void testInit() {
        form.init();
        verify(form).configureFields();
        verify(form).populateFields();
        verify(form).setBindings();
        verify(form).configureEnterShortcut(form.description);
        assertEquals(8, form.getChildren().count());
    }

    @Test
    public void testConfigureFields() {
        form.configureFields();
        verify(form, times(1)).setFieldsRequired();

        assertEquals("Frequency", form.frequency.getLabel());
        assertEquals("Day of week", form.dayOfWeek.getLabel());

        assertTrue(form.frequency.isRequiredIndicatorVisible());
        assertTrue(form.dayOfWeek.isRequiredIndicatorVisible());

        assertEquals(LocalTime.of(7, 0), form.startTime.getMin());
        assertEquals(LocalTime.of(21, 45), form.startTime.getMax());

        assertEquals(LocalTime.of(7, 15), form.endTime.getMin());
        assertEquals(LocalTime.of(22, 0), form.endTime.getMax());
    }

    @Test
    public void testPopulateFields() {
        form.populateFields();
        assertEquals(Class.ClassFrequency.values().length, form.frequency.getDataProvider().size(new Query<>()));
        assertEquals(7, form.dayOfWeek.getDataProvider().size(new Query<>()));
        verify(courseService, times(1)).findAll();
        verify(classGroupService, times(1)).findAll();
        verify(teacherInfoService, times(1)).findAll();
    }

    @Test
    public void testSetBindings() {
        form.setBindings();
        assertNotNull(form.getBinder().getBinding("course"));
        assertNotNull(form.getBinder().getBinding("classGroup"));
        assertNotNull(form.getBinder().getBinding("teachers"));
        assertNotNull(form.getBinder().getBinding("dayOfWeek"));
        assertNotNull(form.getBinder().getBinding("frequency"));
        assertNotNull(form.getBinder().getBinding("startTime"));
        assertNotNull(form.getBinder().getBinding("endTime"));
        assertNotNull(form.getBinder().getBinding("type"));
        assertNotNull(form.getBinder().getBinding("location"));
        assertNotNull(form.getBinder().getBinding("description"));
    }

    @Test
    public void testCourseBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        Course course = new Course();
        course.setName("Test course");

        Course course2 = new Course();
        course2.setName("Test course 2");
        form.course.setItems(List.of(course, course2));

        assertNull(bean.getCourse());
        form.course.setValue(course2);
        assertNotNull(bean.getCourse());
        assertEquals("Test course 2", bean.getCourse().getName());
    }

    @Test
    public void testClassGroupBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("Test class group");

        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setName("Test class group 2");
        form.classGroup.setItems(List.of(classGroup, classGroup2));

        assertNull(bean.getClassGroup());
        form.classGroup.setValue(classGroup2);
        assertNotNull(bean.getClassGroup());
        assertEquals("Test class group 2", bean.getClassGroup().getName());
    }

    @Test
    public void testDayOfWeekBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, bean.getDayOfWeek());
    }

    @Test
    public void testFrequencyBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        assertNotEquals(Class.DEFAULT_FREQUENCY, Class.ClassFrequency.ODD_WEEKS);
        form.frequency.setValue(Class.ClassFrequency.ODD_WEEKS);
        assertEquals(Class.ClassFrequency.ODD_WEEKS, bean.getFrequency());
    }

    @Test
    public void testStartTimeBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.startTime.setValue(LocalTime.of(8, 0));
        assertEquals(LocalTime.of(8, 0), bean.getStartTime());
    }

    @Test
    public void testEndTimeBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.endTime.setValue(LocalTime.of(9, 30));
        assertEquals(LocalTime.of(9, 30), bean.getEndTime());
    }

    @Test
    public void testTeachersBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        TeacherInfo teacher1 = new TeacherInfo();
        teacher1.setName("Test teacher");
        TeacherInfo teacher2 = new TeacherInfo();
        teacher2.setName("Test teacher 2");
        List<TeacherInfo> teachers = List.of(teacher1, teacher2);
        form.teachers.setItems(teachers);
        form.teachers.setValue(Set.of(teacher2));
        assertEquals(1, bean.getTeachers().size());
    }

    @Test
    public void testTypeBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.type.setValue("Test type");
        assertEquals("Test type", bean.getType());
    }

    @Test
    public void testLocationBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.location.setValue("Test location");
        assertEquals("Test location", bean.getLocation());
    }

    @Test
    public void testDescriptionBinding() {
        form.setBindings();
        Class bean = new Class();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }
}