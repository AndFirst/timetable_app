package pl.bscisel.timetable.data.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vaadin.stefan.fullcalendar.Delta;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.ClassRepository;
import pl.bscisel.timetable.views.timetable.CalendarEntry;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ClassService {
    private final ClassRepository classRepository;

    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public String getTeachersStr(Set<TeacherInfo> teachers) {
        if (teachers.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (TeacherInfo teacher : teachers) {
            sb.append(teacher.getDegree()).append(" ").append(teacher.getName()).append(" ").append(teacher.getSurname()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    @Transactional
    public List<CalendarEntry> makeCalendarEntriesStream(Long classGroupId) {
        List<Class> classes = classRepository.findAllByClassGroupId(classGroupId);
        List<CalendarEntry> calendarEntries = new ArrayList<>();
        for (Class aClass : classes) {
            calendarEntries.add(makeCalendarEntry(aClass));
        }
        return calendarEntries;
    }

    @Transactional
    public Optional<CalendarEntry> getCalendarEntry(Long classId) {
        return classRepository.findById(classId).map(this::makeCalendarEntry);
    }

    public CalendarEntry makeCalendarEntry(Class event) {
        CalendarEntry entry = new CalendarEntry(event.getId());
        entry.setTitle(event.getCourse().getName());
        entry.setTeacher(getTeachersStr(event.getTeachers()));
        entry.setLocation(event.getLocation());
        entry.setDescription(event.getDescription());
        entry.setColor("#0000FF");
        entry.setRecurringStartTime(event.getStartTime());
        entry.setRecurringEndTime(event.getEndTime());
        entry.setRecurringDaysOfWeek(event.getDayOfWeek());
        return entry;
    }

    public void updateClassByDelta(Long classId, Delta delta, boolean onlyEndTime) {
        classRepository.findById(classId).ifPresent(aClass -> {
            if (!onlyEndTime) {
                LocalTime newStartTime = aClass.getStartTime()
                        .plusHours(delta.getHours())
                        .plusMinutes(delta.getMinutes());
                aClass.setStartTime(newStartTime);
            }

            LocalTime newEndTime = aClass.getEndTime()
                    .plusHours(delta.getHours())
                    .plusMinutes(delta.getMinutes());
            aClass.setEndTime(newEndTime);

            DayOfWeek newDayOfWeek = aClass.getDayOfWeek().plus(delta.getDays());
            aClass.setDayOfWeek(newDayOfWeek);
            classRepository.save(aClass);
        });
    }
}
