package pl.bscisel.timetable.views.timetable.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.data.service.TeacherInfoService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
        form = spy(new ClassForm(courseService, classGroupService, teacherInfoService));
    }

    @Test
    public void testRequiredFieldsAreRequired() {
        assertTrue(form.course.isRequired());
        assertTrue(form.classGroup.isRequired());
        assertTrue(form.startTime.isRequired());
        assertTrue(form.endTime.isRequired());
    }

    @Test
    public void testBindingsAreSet() {
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
        Class bean = new Class();
        form.setFormBean(bean);
        Course course = new Course();
        course.setName("Test course");
        form.course.setValue(course);
        assertEquals(course.getName(), bean.getCourse().getName());
    }

    @Test
    public void testClassGroupBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("Test class group");
        form.classGroup.setValue(classGroup);
        assertEquals(classGroup.getName(), bean.getClassGroup().getName());
    }

    @Test
    public void testTeachersBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setName("Test teacher");
        TeacherInfo teacherInfo1 = new TeacherInfo();
        teacherInfo1.setName("Test teacher 1");
        Set<TeacherInfo> teachers = new LinkedHashSet<>(Arrays.asList(teacherInfo, teacherInfo1));
        form.teachers.setValue(teachers);
        assertEquals(2, bean.getTeachers().size());
    }

    @Test
    public void testDayOfWeekBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.dayOfWeek.setValue(DayOfWeek.MONDAY);
        assertEquals(DayOfWeek.MONDAY, bean.getDayOfWeek());
    }

    @Test
    public void testFrequencyBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.frequency.setValue(Class.ClassFrequency.ALL_WEEKS);
        assertEquals(Class.ClassFrequency.ALL_WEEKS, bean.getFrequency());
    }

    @Test
    public void testStartTimeBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.startTime.setValue(LocalTime.of(8, 0));
        assertEquals(LocalTime.of(8, 0), bean.getStartTime());
    }

    @Test
    public void testEndTimeBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.endTime.setValue(LocalTime.of(9, 30));
        assertEquals(LocalTime.of(9, 30), bean.getEndTime());
    }

    @Test
    public void testTypeBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.type.setValue("Test type");
        assertEquals("Test type", bean.getType());
    }

    @Test
    public void testLocationBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.location.setValue("Test location");
        assertEquals("Test location", bean.getLocation());
    }

    @Test
    public void testDescriptionBinding() {
        Class bean = new Class();
        form.setFormBean(bean);
        form.description.setValue("Test description");
        assertEquals("Test description", bean.getDescription());
    }
}