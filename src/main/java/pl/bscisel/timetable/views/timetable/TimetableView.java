package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import elemental.json.Json;
import elemental.json.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import pl.bscisel.timetable.data.service.ClassService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.MainLayout;


@PageTitle("Class group")
@Route(value = "classgroup", layout = MainLayout.class)
@AnonymousAllowed
public class TimetableView extends VerticalLayout implements HasUrlParameter<Long> {
    private final boolean isUserAdmin;
    private final ClassService classService;
    private FullCalendar calendar;
    private Long classGroupId;

    public TimetableView(ClassService classService,
                         SecurityService securityService) {
        this.classService = classService;
        this.isUserAdmin = securityService.isUserAdmin();
        calendar = new MyFullCalendar();

        setCalendarSettings();

        if (isUserAdmin)
            configureAdminView();

        add(new HorizontalLayout(new Button("Add event")));
        configureLayout();
    }

    @NotNull
    private static JsonObject getCalendarSettings(boolean isUserAdmin) {
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
        settings.put("editable", isUserAdmin);
        settings.put("eventStartEditable", isUserAdmin);
        settings.put("eventDurationEditable", isUserAdmin);
        settings.put("selectable", isUserAdmin);
        return settings;
    }

    private void configureLayout() {
        setSizeFull();
        add(calendar);
        setAlignItems(Alignment.STRETCH);
    }

    private void setCalendarSettings() {
        JsonObject settings = getCalendarSettings(isUserAdmin);

        for (String key : settings.keys()) {
            calendar.setOption(key, settings.get(key));
        }
        calendar.changeView(CalendarViewImpl.TIME_GRID_WEEK);
    }

    @Override
    public void setParameter(BeforeEvent event, Long classGroupId) {
        this.classGroupId = classGroupId;
        setEntryProvider();
    }

    private void setEntryProvider() {
        calendar.setEntryProvider(new ClassGroupEntryProvider(classService, classGroupId));
    }

    private void configureAdminView() {
        calendar.addEntryResizedListener((event) -> {
            classService.updateClassByDelta(Long.valueOf(event.getEntry().getId()), event.getDelta(), true);
            Notification.show("Event resized!");
        });

        calendar.addEntryDroppedListener(event -> {
            classService.updateClassByDelta(Long.valueOf(event.getEntry().getId()), event.getDelta(), false);
            Notification.show("Event moved!");
        });

        calendar.addEntryClickedListener(event -> {
            Notification.show("Event clicked!");
        });
    }
}
