package pl.bscisel.timetable.views;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.vaadin.stefan.fullcalendar.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Locale;

@PageTitle("Class group")
@Route(value = "classgroup", layout = MainLayout.class)
@AnonymousAllowed
public class TimetableView extends VerticalLayout  {

    public TimetableView() {
        FullCalendar calendar = FullCalendarBuilder.create().build();
        calendar.setTimeslotsSelectable(true);

        calendar.addEntryResizedListener((event) -> {
            Entry entry = new Entry();

            event.applyChangesOnEntry();
            entry.setColor("dodgerblue");
            Notification.show(event.getJsonObject().toString());
        });

        calendar.setLocale(Locale.ENGLISH);
        calendar.changeView(CalendarViewImpl.TIME_GRID_WEEK);
        add(calendar);
        setFlexGrow(1, calendar);
        setSizeFull();
        setAlignItems(Alignment.STRETCH);

        Entry entry = new Entry();
        entry.setTitle("Some event");
        entry.setColor("#ff3333");
        entry.setRecurringStartTime(LocalTime.of(8, 45));
        entry.setRecurringEndTime(LocalTime.of(10, 45));
        entry.setRecurringDaysOfWeek(DayOfWeek.THURSDAY);
        calendar.setEditable(true);
        calendar.setEntryStartEditable(true);
        calendar.setEntryResizableFromStart(true);
        calendar.setEntryDurationEditable(true);
        calendar.addEntryDroppedListener(event -> {
            Notification.show(event.getEntry().getRecurringStartTimeAsLocalTime().toString());
            Notification.show(event.getEntry().getRecurringEndTimeAsLocalTime().toString());
            Notification.show(event.applyChangesOnEntry().getRecurringStartTimeAsLocalTime().toString());
            Notification.show(event.applyChangesOnEntry().getRecurringEndTimeAsLocalTime().toString());
            Notification.show(event.getJsonObject().toString());
        });

        JsonObject json = Json.createObject();
        json.put("weekday", "long");

        JsonObject json2 = Json.createObject();
        json2.put("start", "title");
        json2.put("center", "");
        json2.put("end", "today prev,next");

        JsonObject json3 = Json.createObject();
        json3.put("start", "2024-01-01");
        json3.put("end", "2024-01-07");


        JsonObject slotLabelFormat= Json.createObject();
        slotLabelFormat.put("hour", "numeric");
        slotLabelFormat.put("minute", "numeric");
        slotLabelFormat.put("hour12", false);

        calendar.setOption("allDaySlot", Json.create(false));
        calendar.setOption("weekends", Json.create(false));
        calendar.setOption("dayHeaderFormat", json);
        calendar.setOption("slotDuration", Json.create("00:15:00"));
        calendar.setOption("slotLabelInterval", Json.create("01:00"));
        calendar.setOption("slotLabelFormat", slotLabelFormat);
        calendar.setOption("slotMinTime", "06:00:00");
        calendar.setOption("slotMaxTime", "22:00:00");
        calendar.setOption("weekNumbers", false);

        calendar.getEntryProvider().asInMemory().addEntries(entry);

    }

}
