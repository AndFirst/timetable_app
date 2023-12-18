package pl.bscisel.timetable.data.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.stefan.fullcalendar.Delta;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.Event;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.ClassRepository;
import pl.bscisel.timetable.data.repository.ConsultationRepository;
import pl.bscisel.timetable.views.timetable.CalendarEntry;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class EventsService {
    private final ClassRepository classRepository;
    private final ConsultationRepository consultationRepository;

    public EventsService(ClassRepository classRepository,
                         ConsultationRepository consultationRepository) {
        this.classRepository = classRepository;
        this.consultationRepository = consultationRepository;
    }

    private static LocalTime addDeltaToLocalTime(LocalTime time, Delta delta) {
        return time
                .plusHours(delta.getHours())
                .plusMinutes(delta.getMinutes());
    }

    public String getTeachersStr(Set<TeacherInfo> teachers) {
        if (teachers.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (TeacherInfo teacher : teachers) {
            sb.append(teacher.getFullName()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    @Transactional
    public List<CalendarEntry> makeClassGroupEntriesStream(Long classGroupId, boolean editable) {
        List<Class> classes = classRepository.findAllByClassGroupId(classGroupId); // unfortunately can't use stream here
        return makeCalendarEntriesForClasses(classes, editable);
    }

    @Transactional
    public List<CalendarEntry> makeTeacherClassesEntriesStream(Long teacherId, boolean editable) {
        List<Class> classes = classRepository.findAllByTeacherId(teacherId);
        return makeCalendarEntriesForClasses(classes, editable);
    }

    @Transactional
    public List<CalendarEntry> makeTeacherConsultationsEntriesStream(Long teacherId, boolean editable) {
        List<Consultation> consultations = consultationRepository.findAllByTeacherId(teacherId);
        return makeCalendarEntriesForConsultations(consultations, editable);
    }

    private List<CalendarEntry> makeCalendarEntriesForConsultations(List<Consultation> consultations, boolean editable) {
        List<CalendarEntry> calendarEntries = new ArrayList<>();
        for (Consultation consultation : consultations) {
            calendarEntries.add(makeEntry(consultation, editable));
        }
        return calendarEntries;
    }

    @NotNull
    private List<CalendarEntry> makeCalendarEntriesForClasses(List<Class> classes, boolean editable) {
        List<CalendarEntry> calendarEntries = new ArrayList<>();
        for (Class aClass : classes) {
            calendarEntries.add(makeEntry(aClass, editable));
        }
        return calendarEntries;
    }

    public CalendarEntry makeEntry(Class event, boolean editable) {
        CalendarEntry entry = new CalendarEntry(event);
        entry.setTitle(event.getCourse().getName());
        entry.setType(event.getType());
        entry.setTeacher(getTeachersStr(event.getTeachers()));
        entry.setColor("#005FDB");
        entry.setEditable(editable);
        entry.setDurationEditable(editable);
        return entry;
    }

    public CalendarEntry makeEntry(Consultation consultation, boolean editable) {
        CalendarEntry entry = new CalendarEntry(consultation);
        entry.setTitle("Consultation");
        entry.setTeacher(consultation.getTeacher().getFullName());
        entry.setColor("#8B008B");
        entry.setEditable(editable);
        entry.setDurationEditable(editable);
        return entry;
    }

    public void updateEventByDelta(Event event, Delta delta, boolean onlyEndTime) {
        if (!onlyEndTime) {
            LocalTime newStartTime = addDeltaToLocalTime(event.getStartTime(), delta);
            event.setStartTime(newStartTime);
        }

        LocalTime newEndTime = addDeltaToLocalTime(event.getEndTime(), delta);
        event.setEndTime(newEndTime);

        DayOfWeek newDayOfWeek = event.getDayOfWeek().plus(delta.getDays());
        event.setDayOfWeek(newDayOfWeek);

        if (event instanceof Class classEvent) {
            classRepository.save(classEvent);
        } else if (event instanceof Consultation consultationEvent) {
            consultationRepository.save(consultationEvent);
        }
    }

    public void saveEvent(Class newClass) {
        classRepository.save(newClass);
    }

    public void deleteEvent(Class aClass) {
        classRepository.delete(aClass);
    }
}
