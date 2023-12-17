package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.stefan.fullcalendar.dataprovider.InMemoryEntryProvider;
import pl.bscisel.timetable.data.service.EventsService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.MainLayout;


@PageTitle("Class group")
@Route(value = "classgroup", layout = MainLayout.class)
@AnonymousAllowed
public class ClassGroupView extends AbstractCalendarView implements HasUrlParameter<Long> {
    private final boolean isUserAdmin;
    private Long classGroupId;

    public ClassGroupView(EventsService eventsService,
                          SecurityService securityService) {
        super(eventsService);
        this.isUserAdmin = securityService.isUserAdmin();
        setListenersForEditing(isUserAdmin);
        if (isUserAdmin) {
            addComponentAsFirst(new HorizontalLayout(new Button("Add event")));
        }
    }

    @Override
    public void setParameter(BeforeEvent event, Long classGroupId) {
        this.classGroupId = classGroupId;
        setEntryProvider();
    }

    private void setEntryProvider() {
        // implementation of AbstractEntryProvider was leading to a multiple db calls, probably a bug in the library
        calendar.setEntryProvider(new InMemoryEntryProvider<>(eventsService.makeClassGroupEntriesStream(classGroupId, isUserAdmin)));
        calendar.getEntryProvider().refreshAll();
    }
}
