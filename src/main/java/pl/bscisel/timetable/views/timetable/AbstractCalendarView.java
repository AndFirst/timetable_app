package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.Timezone;
import pl.bscisel.timetable.data.entity.Event;
import pl.bscisel.timetable.data.service.EventsService;
import pl.bscisel.timetable.views.timetable.components.CalendarEntry;
import pl.bscisel.timetable.views.timetable.components.MyFullCalendar;

import java.time.ZoneId;

// LLR_100
public abstract class AbstractCalendarView extends VerticalLayout {
    protected final EventsService eventsService;
    protected final FullCalendar calendar;

    private Registration entryResizedListener;
    private Registration entryDroppedListener;

    public AbstractCalendarView(EventsService eventsService) {
        this.eventsService = eventsService;
        calendar = new MyFullCalendar();
        setCalendarSettings();
        configureLayout();
    }

    protected void setListenersForMovingAndResizing(boolean editableView) {
        if (editableView) {
            addListenersForMovingAndResizing();
            calendar.setTimeslotsSelectable(true);
        } else {
            removeListenersForMovingAndResizing();
            calendar.setTimeslotsSelectable(false);
        }
    }

    private void addListenersForMovingAndResizing() {
        if (entryResizedListener != null || entryDroppedListener != null) {
            return;
        }
        entryResizedListener = calendar.addEntryResizedListener((event) -> {
            if (isEntryNotEditable(event.getEntry())) return;

            CalendarEntry calendarEntry = (CalendarEntry) event.getEntry();
            Event dbEvent = calendarEntry.getEvent();
            calendarEntry.setRecurringEndTime(dbEvent.getEndTime().plusHours(event.getDelta().getHours()).plusMinutes(event.getDelta().getMinutes()));
            eventsService.updateEventByDelta(dbEvent, event.getDelta(), true);
            Notification.show("Event resized!");
        });

        entryDroppedListener = calendar.addEntryDroppedListener(event -> {
            if (isEntryNotEditable(event.getEntry())) return;

            CalendarEntry calendarEntry = (CalendarEntry) event.getEntry();
            Event dbEvent = calendarEntry.getEvent();
            calendarEntry.setRecurringStartTime(dbEvent.getStartTime().plusHours(event.getDelta().getHours()).plusMinutes(event.getDelta().getMinutes()));
            calendarEntry.setRecurringEndTime(dbEvent.getEndTime().plusHours(event.getDelta().getHours()).plusMinutes(event.getDelta().getMinutes()));
            calendarEntry.setRecurringDaysOfWeek(dbEvent.getDayOfWeek().plus(event.getDelta().getDays()));
            eventsService.updateEventByDelta(dbEvent, event.getDelta(), false);
            Notification.show("Event moved!");
        });
    }

    private boolean isEntryNotEditable(Entry event) {
        return !(event instanceof CalendarEntry entry) || !entry.isEditable();
    }

    private void removeListenersForMovingAndResizing() {
        if (entryResizedListener != null) {
            entryResizedListener.remove();
            entryResizedListener = null;
        }

        if (entryDroppedListener != null) {
            entryDroppedListener.remove();
            entryDroppedListener = null;
        }
    }

    protected void setCalendarSettings() {
        JsonObject settings = getCalendarSettings();

        for (String key : settings.keys()) {
            calendar.setOption(key, settings.get(key));
        }

        calendar.setTimezone(new Timezone(ZoneId.of("Europe/Warsaw")));
        calendar.changeView(CalendarViewImpl.TIME_GRID_WEEK);
    }

    private void configureLayout() {
        setSizeFull();
        add(calendar);
        setAlignItems(Alignment.STRETCH);
    }

    private JsonObject getCalendarSettings() {
        JsonObject settings = Json.createObject();

        JsonObject dayHeaderFormat = Json.createObject();
        dayHeaderFormat.put("weekday", "long");

        settings.put("dayHeaderFormat", dayHeaderFormat);

        JsonObject slotLabelFormat = Json.createObject();
        slotLabelFormat.put("hour", "numeric");
        slotLabelFormat.put("minute", "numeric");
        slotLabelFormat.put("hour12", false);

        JsonObject eventTimeFormat = Json.createObject();
        eventTimeFormat.put("hour", "2-digit");
        eventTimeFormat.put("minute", "2-digit");
        eventTimeFormat.put("hour12", false);

        settings.put("slotDuration", "00:30:00");
        settings.put("slotLabelInterval", "01:00");
        settings.put("slotLabelFormat", slotLabelFormat);
        settings.put("slotMinTime", "07:00:00");
        settings.put("slotMaxTime", "22:00:00");
        settings.put("weekNumbers", false);
        settings.put("allDaySlot", false);
        settings.put("weekends", false);
        settings.put("initialView", "timeGridWeek");
        settings.put("locale", "en");
        settings.put("eventTimeFormat", eventTimeFormat);
        return settings;
    }
}
