package pl.bscisel.timetable.views.timetable;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.vaadin.stefan.fullcalendar.dataprovider.InMemoryEntryProvider;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.data.service.EventsService;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.MainLayout;
import pl.bscisel.timetable.views.timetable.forms.ClassForm;

import java.time.DayOfWeek;
import java.time.LocalTime;


@PageTitle("Class group")
@Route(value = "classgroup", layout = MainLayout.class)
@AnonymousAllowed
public class ClassGroupView extends AbstractCalendarView implements HasUrlParameter<Long> {
    private final CourseService courseService;
    private final ClassGroupService classGroupService;
    private final TeacherInfoService teacherInfoService;
    private final boolean isUserAdmin;

    private Long classGroupId;
    private Button addClassBtn;
    private Dialog addClassDialog;
    private ClassForm classForm;
    private InMemoryEntryProvider<CalendarEntry> entryProvider;

    private LocalTime startSelected;
    private LocalTime endSelected;
    private DayOfWeek dayOfWeekSelected;
    private CalendarEntry editedEntry;


    public ClassGroupView(EventsService eventsService,
                          SecurityService securityService,
                          CourseService courseService,
                          ClassGroupService classGroupService,
                          TeacherInfoService teacherInfoService) {
        super(eventsService);
        this.courseService = courseService;
        this.classGroupService = classGroupService;
        this.teacherInfoService = teacherInfoService;
        this.isUserAdmin = securityService.isUserAdmin();

        if (isUserAdmin) {
            configureToolbar();
            configureCalendarListeners();
            configureForm();
            configureDialog();
        }
    }

    private void configureToolbar() {
        addClassBtn = new Button("Add event");
        addClassBtn.addClickListener(ignored -> addNewClass());
        addComponentAsFirst(new HorizontalLayout(addClassBtn));
    }

    private void configureCalendarListeners() {
        calendar.addTimeslotsSelectedListener(event -> {
            startSelected = event.getStartWithOffset().toLocalTime();
            endSelected = event.getEndWithOffset().toLocalTime();
            dayOfWeekSelected = event.getStartWithOffset().getDayOfWeek();
        });

        calendar.addEntryClickedListener(event -> {
            editedEntry = (CalendarEntry) event.getEntry();
            editClass((Class) ((CalendarEntry) event.getEntry()).getEvent());
        });

        setListenersForMovingAndResizing(true);
    }

    private void configureForm() {
        createForm();
        configureFormListeners();
    }

    private void createForm() {
        classForm = new ClassForm(courseService, classGroupService, teacherInfoService);
    }

    private void configureFormListeners() {
        classForm.addSaveAction(aClass -> {
            eventsService.saveEvent(aClass);
            removeEditedEntry();
            entryProvider.addEntry(eventsService.makeEntry(aClass, true));
            entryProvider.refreshAll();
            addClassDialog.close();
        });

        classForm.addDeleteAction(aClass -> {
            eventsService.deleteEvent(aClass);
            removeEditedEntry();
            entryProvider.refreshAll();
            addClassDialog.close();
        });

        classForm.addCancelAction(ignored -> addClassDialog.close());
    }

    private void removeEditedEntry() {
        if (editedEntry != null) {
            entryProvider.removeEntry(editedEntry);
        }
    }

    private void configureDialog() {
        createDialog();
        configureDialogLayout();
        configureDialogListeners();
    }

    private void configureDialogLayout() {
        addClassDialog.add(classForm);
        addClassDialog.getFooter().add(classForm.getButtons());
        addClassDialog.setHeaderTitle("Add class to schedule");
        addClassDialog.setResizable(true);
        addClassDialog.setDraggable(true);
        addClassDialog.setCloseOnEsc(false); // form has it
        addClassDialog.setCloseOnOutsideClick(true);
        addClassDialog.setWidth("500px");
    }

    private void createDialog() {
        addClassDialog = new Dialog();
    }

    private void configureDialogListeners() {
        addClassDialog.addDialogCloseActionListener(ignored -> {
            classForm.setFormBean(null);
            editedEntry = null;
            addClassDialog.close();
        });
    }

    private void addNewClass() {
        Class newClassEntity = new Class();
        classGroupService.findById(classGroupId).ifPresent(newClassEntity::setClassGroup);
        newClassEntity.setStartTime(startSelected);
        newClassEntity.setEndTime(endSelected);
        newClassEntity.setDayOfWeek(dayOfWeekSelected);

        editClass(newClassEntity);
    }

    private void editClass(Class entry) {
        classForm.setFormBean(entry);
        addClassDialog.open();
    }

    @Override
    public void setParameter(BeforeEvent event, Long classGroupId) {
        this.classGroupId = classGroupId;
        setEntryProvider();
    }

    private void setEntryProvider() {
        // implementation of AbstractEntryProvider was leading to a multiple db calls, probably a bug in the library
        entryProvider = new InMemoryEntryProvider<>(eventsService.makeClassGroupEntriesStream(classGroupId, isUserAdmin));
        calendar.setEntryProvider(entryProvider);
        calendar.getEntryProvider().refreshAll();
    }
}
