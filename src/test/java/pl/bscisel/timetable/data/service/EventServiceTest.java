package pl.bscisel.timetable.data.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.vaadin.stefan.fullcalendar.Delta;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.ClassRepository;
import pl.bscisel.timetable.data.repository.ConsultationRepository;
import pl.bscisel.timetable.views.timetable.components.CalendarEntry;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceTest {

    @Mock
    private ClassRepository classRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private EventsService eventsService;

    @NotNull
    private static Class getClass(Course course, String location, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String description, String type) {
        Class class1 = new Class();
        class1.setCourse(course);
        class1.setLocation(location);
        class1.setDayOfWeek(dayOfWeek);
        class1.setStartTime(startTime);
        class1.setEndTime(endTime);
        class1.setDescription(description);
        class1.setType(type);
        return class1;
    }

    @NotNull
    private static Course getCourse(String name) {
        Course course = new Course();
        course.setName(name);
        return course;
    }

    @NotNull
    private static Consultation getConsultation(TeacherInfo teacher, String location, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String description) {
        Consultation consultation = new Consultation();
        consultation.setTeacher(teacher);
        consultation.setLocation(location);
        consultation.setDayOfWeek(dayOfWeek);
        consultation.setStartTime(startTime);
        consultation.setEndTime(endTime);
        consultation.setDescription(description);
        return consultation;
    }

    @NotNull
    private static TeacherInfo getTeacher(String name, String surname, String degree) {
        TeacherInfo teacher1 = new TeacherInfo();
        teacher1.setName(name);
        teacher1.setSurname(surname);
        teacher1.setDegree(degree);
        return teacher1;
    }

    @Test
    public void testGetTeacherStr() {
        TeacherInfo teacher1 = getTeacher("Jan", "Kowalski", "dr");
        TeacherInfo teacher2 = getTeacher("Adam", "Nowak", "mgr");
        TeacherInfo teacher3 = getTeacher("Anna", "Kowalska", "inz");

        String result = eventsService.getTeachersStr(new LinkedHashSet<>(Arrays.asList(teacher1, teacher2, teacher3)));
        assertEquals("dr Jan Kowalski, mgr Adam Nowak, inz Anna Kowalska", result);

        result = eventsService.getTeachersStr(Set.of());
        assertEquals("", result);

        result = eventsService.getTeachersStr(Set.of(teacher1));
        assertEquals("dr Jan Kowalski", result);

        result = eventsService.getTeachersStr(new LinkedHashSet<>(Arrays.asList(teacher1, teacher2)));
        assertEquals("dr Jan Kowalski, mgr Adam Nowak", result);

        result = eventsService.getTeachersStr(null);
        assertEquals("", result);
    }

    @Test
    public void testMakeEntryClass() {
        Course course = getCourse("Course 1");
        Class class1 = getClass(course, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1", "Type 1");

        TeacherInfo teacher1 = getTeacher("Jan", "Kowalski", "dr");
        Set<TeacherInfo> teachers = Set.of(teacher1);
        class1.setTeachers(teachers);

        EventsService eventsService = spy(this.eventsService);
        CalendarEntry calendarEntry = eventsService.makeEntry(class1, true);

        verify(eventsService, times(1)).getTeachersStr(teachers);

        assertEquals("Course 1", calendarEntry.getTitle());
        assertEquals("Room 1", calendarEntry.getCustomProperty("location"));
        assertEquals(Set.of(DayOfWeek.MONDAY), calendarEntry.getRecurringDaysOfWeek());
        assertEquals(LocalTime.of(8, 0), calendarEntry.getRecurringStartTime().toLocalTime());
        assertEquals(LocalTime.of(9, 30), calendarEntry.getRecurringEndTime().toLocalTime());
        assertEquals("Description 1", calendarEntry.getDescription());
        assertEquals("Type 1", calendarEntry.getCustomProperty("type"));
        assertEquals("dr Jan Kowalski", calendarEntry.getCustomProperty("teacher"));
        assertEquals("#005FDB", calendarEntry.getColor());
        assertTrue(calendarEntry.isEditable());
        assertTrue(calendarEntry.isDurationEditable());
    }

    @Test
    public void testMakeEntryConsultation() {
        TeacherInfo teacher = getTeacher("Jan", "Kowalski", "dr");
        Consultation consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");
        CalendarEntry calendarEntry = eventsService.makeEntry(consultation, true);

        assertEquals("Consultation", calendarEntry.getTitle());
        assertEquals("Room 1", calendarEntry.getCustomProperty("location"));
        assertEquals(Set.of(DayOfWeek.MONDAY), calendarEntry.getRecurringDaysOfWeek());
        assertEquals(LocalTime.of(8, 0), calendarEntry.getRecurringStartTime().toLocalTime());
        assertEquals(LocalTime.of(9, 30), calendarEntry.getRecurringEndTime().toLocalTime());
        assertEquals("Description 1", calendarEntry.getDescription());
        assertEquals("dr Jan Kowalski", calendarEntry.getCustomProperty("teacher"));
        assertEquals("#8B008B", calendarEntry.getColor());
        assertTrue(calendarEntry.isEditable());
        assertTrue(calendarEntry.isDurationEditable());
    }

    @Test
    public void testSaveClass() {
        Class class1 = new Class();
        when(classRepository.save(class1)).thenReturn(class1);

        eventsService.save(class1);

        verify(classRepository, times(1)).save(class1);
    }

    @Test
    public void testDeleteClass() {
        Class class1 = new Class();
        doNothing().when(classRepository).delete(class1);

        eventsService.delete(class1);

        verify(classRepository, times(1)).delete(class1);
    }

    @Test
    public void testSaveConsultation() {
        TeacherInfo teacher = getTeacher("Jan", "Kowalski", "dr");
        Consultation consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");
        when(consultationRepository.save(consultation)).thenReturn(consultation);

        eventsService.save(consultation);

        verify(consultationRepository, times(1)).save(consultation);
    }

    @Test
    public void testDeleteConsultation() {
        TeacherInfo teacher = getTeacher("Jan", "Kowalski", "dr");
        Consultation consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");
        doNothing().when(consultationRepository).delete(consultation);

        eventsService.delete(consultation);

        verify(consultationRepository, times(1)).delete(consultation);
    }

    @Test
    public void testUpdateEventByDelta() {
        Class aClass = getClass(getCourse("Course 1"), "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1", "Type 1");
        TeacherInfo teacher = getTeacher("Jan", "Kowalski", "dr");
        Consultation consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");

        Delta delta = new Delta(0, 0, 1, 1, 1, 0);

        when(classRepository.save(aClass)).thenReturn(aClass);
        when(consultationRepository.save(consultation)).thenReturn(consultation);

        eventsService.updateEventByDelta(aClass, delta, false);
        assertEquals(LocalTime.of(9, 1), aClass.getStartTime());
        assertEquals(LocalTime.of(10, 31), aClass.getEndTime());
        assertEquals(DayOfWeek.TUESDAY, aClass.getDayOfWeek());
        verify(classRepository, times(1)).save(aClass);

        eventsService.updateEventByDelta(consultation, delta, false);
        assertEquals(LocalTime.of(9, 1), consultation.getStartTime());
        assertEquals(LocalTime.of(10, 31), consultation.getEndTime());
        assertEquals(DayOfWeek.TUESDAY, consultation.getDayOfWeek());
        verify(consultationRepository, times(1)).save(consultation);

        aClass = getClass(getCourse("Course 1"), "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1", "Type 1");
        eventsService.updateEventByDelta(aClass, delta, true);
        assertEquals(LocalTime.of(8, 0), aClass.getStartTime());
        assertEquals(LocalTime.of(10, 31), aClass.getEndTime());
        assertEquals(DayOfWeek.TUESDAY, aClass.getDayOfWeek());
        verify(classRepository, times(1)).save(aClass);

        consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");
        eventsService.updateEventByDelta(consultation, delta, true);
        assertEquals(LocalTime.of(8, 0), consultation.getStartTime());
        assertEquals(LocalTime.of(10, 31), consultation.getEndTime());
        assertEquals(DayOfWeek.TUESDAY, consultation.getDayOfWeek());
        verify(consultationRepository, times(1)).save(consultation);
    }

    @Test
    public void testMakeClassGroupEntries() {
        Class aClass = getClass(getCourse("Course 1"), "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1", "Type 1");
        Class aClass2 = getClass(getCourse("Course 2"), "Room 2", DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 30), "Description 2", "Type 2");
        Class aClass3 = getClass(getCourse("Course 3"), "Room 3", DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 3", "Type 3");

        when(classRepository.findAllByClassGroupId(1L)).thenReturn(Arrays.asList(aClass, aClass2, aClass3));

        EventsService eventsService = spy(this.eventsService);

        List<CalendarEntry> calendarEntries = eventsService.makeClassGroupEntries(1L, true);

        assertEquals(3, calendarEntries.size());
        verify(eventsService, times(1)).makeEntry(aClass, true);
        verify(eventsService, times(1)).makeEntry(aClass2, true);
        verify(eventsService, times(1)).makeEntry(aClass3, true);
    }

    @Test
    public void testMakeTeacherClassesEntries() {
        Class aClass = getClass(getCourse("Course 1"), "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1", "Type 1");
        Class aClass2 = getClass(getCourse("Course 2"), "Room 2", DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 30), "Description 2", "Type 2");
        Class aClass3 = getClass(getCourse("Course 3"), "Room 3", DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 3", "Type 3");

        when(classRepository.findAllByTeacherId(1L)).thenReturn(Arrays.asList(aClass, aClass2, aClass3));

        EventsService eventsService = spy(this.eventsService);

        List<CalendarEntry> calendarEntries = eventsService.makeTeacherClassesEntries(1L, true);

        assertEquals(3, calendarEntries.size());
        verify(eventsService, times(1)).makeEntry(aClass, true);
        verify(eventsService, times(1)).makeEntry(aClass2, true);
        verify(eventsService, times(1)).makeEntry(aClass3, true);
    }

    @Test
    public void testMakeTeacherConsultationsEntries() {
        TeacherInfo teacher = getTeacher("Jan", "Kowalski", "dr");
        Consultation consultation = getConsultation(teacher, "Room 1", DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 1");
        Consultation consultation2 = getConsultation(teacher, "Room 2", DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 30), "Description 2");
        Consultation consultation3 = getConsultation(teacher, "Room 3", DayOfWeek.TUESDAY, LocalTime.of(8, 0), LocalTime.of(9, 30), "Description 3");

        when(consultationRepository.findAllByTeacherId(1L)).thenReturn(Arrays.asList(consultation, consultation2, consultation3));

        EventsService eventsService = spy(this.eventsService);

        List<CalendarEntry> calendarEntries = eventsService.makeTeacherConsultationsEntries(1L, true);

        assertEquals(3, calendarEntries.size());
        verify(eventsService, times(1)).makeEntry(consultation, true);
        verify(eventsService, times(1)).makeEntry(consultation2, true);
        verify(eventsService, times(1)).makeEntry(consultation3, true);
    }
}
