package pl.bscisel.timetable.views.timetable;

import lombok.NonNull;
import org.vaadin.stefan.fullcalendar.dataprovider.AbstractEntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryProvider;
import org.vaadin.stefan.fullcalendar.dataprovider.EntryQuery;
import pl.bscisel.timetable.data.service.ClassService;

import java.util.Optional;
import java.util.stream.Stream;

public class ClassGroupEntryProvider extends AbstractEntryProvider<CalendarEntry> implements EntryProvider<CalendarEntry> {

    private final Long classGroupId;
    private final ClassService classService;

    public ClassGroupEntryProvider(ClassService classService,
                                   Long classGroupId) {
        this.classService = classService;
        this.classGroupId = classGroupId;
    }

    @Override
    public Stream<CalendarEntry> fetch(@NonNull EntryQuery query) {
        return classService.makeCalendarEntriesStream(classGroupId).stream();
    }

    @Override
    public Optional<CalendarEntry> fetchById(@NonNull String id) {
        return classService.getCalendarEntry(Long.valueOf(id));
    }

}
