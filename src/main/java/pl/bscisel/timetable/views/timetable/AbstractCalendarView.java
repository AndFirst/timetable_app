package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import pl.bscisel.timetable.data.service.EventsService;

public abstract class AbstractCalendarView extends VerticalLayout {
    protected final EventsService eventsService;
    protected final FullCalendar calendar;

    private Registration entryResizedListener;
    private Registration entryDroppedListener;
    private Registration entryClickedListener;

    public AbstractCalendarView(EventsService eventsService) {
        this.eventsService = eventsService;
        calendar = new MyFullCalendar();
        setCalendarSettings();
        configureLayout();
    }

    protected void setListenersForEditing(boolean editableView) {
        if (editableView) {
            addListenersForEditing();
            calendar.setTimeslotsSelectable(true);
        } else {
            removeListenersForEditing();
            calendar.setTimeslotsSelectable(false);
        }
    }

    private void addListenersForEditing() {
        if (entryResizedListener != null || entryDroppedListener != null || entryClickedListener != null) {
            return;
        }
        entryResizedListener = calendar.addEntryResizedListener((event) -> {
            if (isEntryNotEditable(event.getEntry())) return;

            eventsService.updateEventByDelta(((CalendarEntry) event.getEntry()).getEvent(), event.getDelta(), true);
            Notification.show("Event resized!");
        });

        entryDroppedListener = calendar.addEntryDroppedListener(event -> {
            if (isEntryNotEditable(event.getEntry())) return;

            eventsService.updateEventByDelta(((CalendarEntry) event.getEntry()).getEvent(), event.getDelta(), false);
            Notification.show("Event moved!");
        });

        entryClickedListener = calendar.addEntryClickedListener(event -> {
            if (isEntryNotEditable(event.getEntry())) return;

            Notification.show("Event clicked!");
        });
    }

    private boolean isEntryNotEditable(Entry event) {
        return !(event instanceof CalendarEntry entry) || !entry.isEditable();
    }

    private void removeListenersForEditing() {
        if (entryResizedListener != null) {
            entryResizedListener.remove();
        }

        if (entryDroppedListener != null) {
            entryDroppedListener.remove();
        }

        if (entryClickedListener != null) {
            entryClickedListener.remove();
        }
    }

    protected void setCalendarSettings() {
        JsonObject settings = getCalendarSettings();

        for (String key : settings.keys()) {
            calendar.setOption(key, settings.get(key));
        }

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

        settings.put("slotDuration", "00:15:00");
        settings.put("slotLabelInterval", "01:00");
        settings.put("slotLabelFormat", slotLabelFormat);
        settings.put("slotMinTime", "07:00:00");
        settings.put("slotMaxTime", "22:00:00");
        settings.put("weekNumbers", false);
        settings.put("allDaySlot", false);
        settings.put("weekends", false);
        settings.put("initialView", "timeGridWeek");
        settings.put("locale", "en");
        settings.put("timeZone", "Poland");
        return settings;
    }
}
