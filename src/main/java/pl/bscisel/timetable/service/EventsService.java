package pl.bscisel.timetable.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.stefan.fullcalendar.Delta;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.Event;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.ClassRepository;
import pl.bscisel.timetable.data.repository.ConsultationRepository;
import pl.bscisel.timetable.view.timetables.components.TimetableEntry;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing events.
 */
@Service
public class EventsService {
    private final ClassRepository classRepository;
    private final ConsultationRepository consultationRepository;

    /**
     * Constructs an instance of EventsService.
     */
    public EventsService(ClassRepository classRepository,
                         ConsultationRepository consultationRepository) {
        this.classRepository = classRepository;
        this.consultationRepository = consultationRepository;
    }

    private static LocalTime addDeltaToLocalTime(@NotNull LocalTime time, @NotNull Delta delta) {
        return time
                .plusHours(delta.getHours())
                .plusMinutes(delta.getMinutes());
    }

    /**
     * Generates a comma-separated string of teacher full names from the provided set.
     *
     * @param teachers The set of TeacherInfo objects.
     * @return A formatted string of teacher full names.
     */
    public String getTeachersStr(@Nullable Set<TeacherInfo> teachers) {
        if (teachers == null || teachers.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (TeacherInfo teacher : teachers) {
            sb.append(teacher.getFullName()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    /**
     * Creates calendar entries for classes associated with the specified class group.
     *
     * @param classGroupId The id of the class group.
     * @param editable Whether the entries should be editable.
     * @return A list of calendar entries for the classes.
     */
    @Transactional
    public List<TimetableEntry> makeClassGroupEntries(@NotNull Long classGroupId, boolean editable) {
        List<Class> classes = classRepository.findAllByClassGroupId(classGroupId); // unfortunately can't use stream here
        return makeCalendarEntriesForClasses(classes, editable);
    }

    /**
     * Creates calendar entries for classes associated with the specified teacher.
     *
     * @param teacherId The id of the teacher.
     * @param editable Whether the entries should be editable.
     * @return A list of calendar entries for the classes.
     */
    @Transactional
    public List<TimetableEntry> makeTeacherClassesEntries(@NotNull Long teacherId, boolean editable) {
        List<Class> classes = classRepository.findAllByTeacherId(teacherId);
        return makeCalendarEntriesForClasses(classes, editable);
    }

    /**
     * Creates calendar entries for consultations associated with the specified teacher.
     * @param teacherId The id of the teacher.
     * @param editable Whether the entries should be editable.
     * @return A list of calendar entries for the consultations.
     */
    @Transactional
    public List<TimetableEntry> makeTeacherConsultationsEntries(@NotNull Long teacherId, boolean editable) {
        List<Consultation> consultations = consultationRepository.findAllByTeacherId(teacherId);
        return makeCalendarEntriesForConsultations(consultations, editable);
    }

    private List<TimetableEntry> makeCalendarEntriesForConsultations(@NotNull List<Consultation> consultations, boolean editable) {
        List<TimetableEntry> calendarEntries = new ArrayList<>();
        for (Consultation consultation : consultations) {
            calendarEntries.add(makeEntry(consultation, editable));
        }
        return calendarEntries;
    }

    @NotNull
    private List<TimetableEntry> makeCalendarEntriesForClasses(@NotNull List<Class> classes, boolean editable) {
        List<TimetableEntry> calendarEntries = new ArrayList<>();
        for (Class aClass : classes) {
            calendarEntries.add(makeEntry(aClass, editable));
        }
        return calendarEntries;
    }

    /**
     * Creates a calendar entry for the specified class.
     *
     * @param event The class for which to create the entry.
     * @param editable Whether the entry should be editable.
     * @return A calendar entry for the class.
     */
    public TimetableEntry makeEntry(@NotNull Class event, boolean editable) {
        TimetableEntry entry = new TimetableEntry(event);
        entry.setTitle(event.getCourse().getName());
        entry.setType(event.getType());
        entry.setTeacher(getTeachersStr(event.getTeachers()));
        entry.setColor("#005FDB");
        entry.setEditable(editable);
        entry.setDurationEditable(editable);
        return entry;
    }

    /**
     * Creates a calendar entry for the specified consultation.
     *
     * @param consultation The consultation for which to create the entry.
     * @param editable Whether the entry should be editable.
     * @return A calendar entry for the consultation.
     */
    public TimetableEntry makeEntry(@NotNull Consultation consultation, boolean editable) {
        TimetableEntry entry = new TimetableEntry(consultation);
        entry.setTitle("Consultation");
        entry.setTeacher(consultation.getTeacher().getFullName());
        entry.setColor("#8B008B");
        entry.setEditable(editable);
        entry.setDurationEditable(editable);
        return entry;
    }

    /**
     * Updates the start and end times of an event based on the provided delta.
     *
     * @param event The event to be updated.
     * @param delta The time difference to be applied.
     * @param onlyEndTime Whether to update only the end time.
     */
    public void updateEventByDelta(@NotNull Event event, @NotNull Delta delta, boolean onlyEndTime) {
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

    /**
     * Saves the provided class.
     * @param newClass The class to be saved.
     */
    public void save(@NotNull Class newClass) {
        classRepository.save(newClass);
    }

    /**
     * Deletes the provided class.
     * @param aClass The class to be deleted.
     */
    public void delete(@NotNull Class aClass) {
        classRepository.delete(aClass);
    }

    /**
     * Saves the provided consultation.
     * @param consultation The consultation to be saved.
     */
    public void save(@NotNull Consultation consultation) {
        consultationRepository.save(consultation);
    }

    /**
     * Deletes the provided consultation.
     * @param consultation The consultation to be deleted.
     */
    public void delete(@NotNull Consultation consultation) {
        consultationRepository.delete(consultation);
    }
}
