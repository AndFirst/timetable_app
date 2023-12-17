package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.stefan.fullcalendar.dataprovider.InMemoryEntryProvider;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.EventsService;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.MainLayout;

@PageTitle("Teacher")
@Route(value = "teacher", layout = MainLayout.class)
@AnonymousAllowed
public class TeacherView extends AbstractCalendarView implements HasUrlParameter<Long> {
    private final TeacherInfoService teacherInfoService;
    private final SecurityService securityService;
    boolean isUserAdmin;
    private Long teacherId;
    private HorizontalLayout toolbar;

    public TeacherView(EventsService eventsService,
                       TeacherInfoService teacherInfoService,
                       SecurityService securityService) {
        super(eventsService);
        this.teacherInfoService = teacherInfoService;
        this.securityService = securityService;
        this.isUserAdmin = securityService.isUserAdmin();
    }

    private boolean isLoggedTeacherViewingOwnTimetable() {
        User loggedUser = securityService.getTimetableUserOfAuthenticatedTeacher();
        if (loggedUser == null) {
            return false;
        }
        return teacherInfoService.findTeacherByUserId(loggedUser.getId())
                .map(teacher -> teacher.getId().equals(teacherId)).orElse(false);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long teacherId) {
        this.teacherId = teacherId;
        boolean loggedUserViewingOwnTimetable = isLoggedTeacherViewingOwnTimetable();
        setListenersForEditing(isUserAdmin || loggedUserViewingOwnTimetable);
        setEntryProvider(loggedUserViewingOwnTimetable);
        setToolbar(loggedUserViewingOwnTimetable);
    }

    private void setToolbar(boolean loggedUserViewingOwnTimetable) {
        if (isUserAdmin || loggedUserViewingOwnTimetable) {
            addToolbar();
        } else {
            removeToolbar();
        }
    }

    private void removeToolbar() {
        if (toolbar != null) {
            remove(toolbar);
            toolbar = null;
        }
    }

    private void addToolbar() {
        if (toolbar == null) {
            toolbar = new HorizontalLayout(new Button("Add teacher consultation"));
            addComponentAsFirst(toolbar);
        }
    }

    private void setEntryProvider(boolean loggedUserViewingOwnTimetable) {
        // implementation of AbstractEntryProvider was leading to a multiple db calls, probably a bug in the library
        InMemoryEntryProvider<CalendarEntry> entryProvider = new InMemoryEntryProvider<>();
        entryProvider.addEntries(eventsService.makeTeacherClassesEntriesStream(teacherId, isUserAdmin));
        entryProvider.addEntries(eventsService.makeTeacherConsultationsEntriesStream(teacherId, isUserAdmin || loggedUserViewingOwnTimetable));
        calendar.setEntryProvider(entryProvider);
        calendar.getEntryProvider().refreshAll();
    }
}
