package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.shared.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.stefan.fullcalendar.dataprovider.InMemoryEntryProvider;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Event;
import pl.bscisel.timetable.data.service.EventsService;
import pl.bscisel.timetable.views.timetable.components.CalendarEntry;
import pl.bscisel.timetable.views.timetable.components.FormDialog;
import pl.bscisel.timetable.views.timetable.forms.ClassForm;

import java.time.DayOfWeek;
import java.time.LocalTime;

public abstract class AbstractClassCalendarView extends AbstractCalendarView {
    private static final Logger logger = LoggerFactory.getLogger(AbstractClassCalendarView.class);

    protected InMemoryEntryProvider<CalendarEntry> entryProvider;

    protected ClassForm classForm;
    protected Component classFormButtons;
    protected FormDialog addClassDialog;

    private LocalTime startSelected;
    private LocalTime endSelected;
    private DayOfWeek dayOfWeekSelected;
    protected CalendarEntry editedEntry;

    protected Registration slotsSelectedListener;

    public AbstractClassCalendarView(EventsService eventsService) {
        super(eventsService);
    }

    protected void addNewClass() {
        logger.debug("Adding new class");
        Class newClassEntity = new Class();
        presetClass(newClassEntity);
        editClass(newClassEntity);
    }

    protected void presetClass(Class newClassEntity) {
        setSelectedTimeslotsData(newClassEntity);
    }

    protected void setSelectedTimeslotsData(Event event) {
        event.setStartTime(startSelected);
        event.setEndTime(endSelected);
        event.setDayOfWeek(dayOfWeekSelected);
    }

    protected void editClass(Class entry) {
        classForm.setFormBean(entry);
        addClassDialog.open();
    }

    protected void configureTimeslotsListeners() {
        if (slotsSelectedListener != null) {
            return;
        }

        slotsSelectedListener = calendar.addTimeslotsSelectedListener(event -> {
            logger.debug("Timeslots selected");
            startSelected = event.getStartWithOffset().toLocalTime();
            endSelected = event.getEndWithOffset().toLocalTime();
            dayOfWeekSelected = event.getStartWithOffset().getDayOfWeek();
            editedEntry = null;
        });
    }

    protected void saveClassAction(Class aClass) {
        logger.debug("saveClassAction");
        eventsService.saveEvent(aClass);
        removeEditedEntry();
        entryProvider.addEntry(eventsService.makeEntry(aClass, true));
        entryProvider.refreshAll();
        cleanAndCloseDialog(addClassDialog);
    }

    protected void deleteClassAction(Class aClass) {
        logger.debug("deleteClassAction");
        eventsService.deleteEvent(aClass);
        removeEditedEntry();
        entryProvider.refreshAll();
        cleanAndCloseDialog(addClassDialog);
    }

    protected void removeEditedEntry() {
        if (editedEntry != null) {
            entryProvider.removeEntry(editedEntry);
            logger.debug("removeEditedEntry");
        }
    }

    protected void cleanAndCloseDialog(Dialog dialog) {
        cleanAfterEditing();
        dialog.close();
    }

    protected void cleanAfterEditing() {
        logger.debug("cleanAfterEditing");
        editedEntry = null;
        classForm.setFormBean(null);
    }
}
