package pl.bscisel.timetable.view.timetables;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.shared.Registration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.vaadin.stefan.fullcalendar.dataprovider.InMemoryEntryProvider;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.CourseService;
import pl.bscisel.timetable.service.EventsService;
import pl.bscisel.timetable.service.TeacherInfoService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.view.layout.MainLayout;
import pl.bscisel.timetable.view.timetables.components.TimetableEntry;
import pl.bscisel.timetable.view.timetables.components.FormDialog;
import pl.bscisel.timetable.form.ClassForm;


@PageTitle("Class group")
@Route(value = "classgroup", layout = MainLayout.class)
@AnonymousAllowed
public class ClassGroupTimetableView extends AbstractClassTimetableView implements HasUrlParameter<Long> {
    private static final Logger logger = LoggerFactory.getLogger(ClassGroupTimetableView.class);

    private final CourseService courseService;
    private final ClassGroupService classGroupService;
    private final TeacherInfoService teacherInfoService;
    private final ApplicationContext applicationContext;
    private final boolean isUserAdmin;

    private Long classGroupId;
    private Button addClassBtn;

    private Registration entryClickedRegistration;

    public ClassGroupTimetableView(EventsService eventsService,
                                   SecurityService securityService,
                                   CourseService courseService,
                                   ClassGroupService classGroupService,
                                   TeacherInfoService teacherInfoService,
                                   ApplicationContext applicationContext) {
        super(eventsService);
        this.courseService = courseService;
        this.classGroupService = classGroupService;
        this.teacherInfoService = teacherInfoService;
        this.applicationContext = applicationContext;
        this.isUserAdmin = securityService.isUserAdmin();

        if (isUserAdmin) {
            configureToolbar();
            configureCalendarListeners();
            configureForm();
            configureFormDialogs();
        }
    }

    private void configureToolbar() {
        addClassBtn = new Button("Add event");
        addClassBtn.addClickListener(ignored -> addNewClass());
        addComponentAsFirst(new HorizontalLayout(addClassBtn));
    }

    private void configureCalendarListeners() {
        configureTimeslotsListeners();
        configureEntryClickedListener();
        setListenersForMovingAndResizing(true);
    }

    private void configureEntryClickedListener() {
        if (entryClickedRegistration != null) {
            return;
        }
        entryClickedRegistration = calendar.addEntryClickedListener(event -> {
            editedEntry = (TimetableEntry) event.getEntry();
            editClass((Class) ((TimetableEntry) event.getEntry()).getEvent());
        });
    }

    private void configureForm() {
        createForm();
        configureFormListeners();
    }

    private void createForm() {
        classForm = applicationContext.getBean(ClassForm.class);
        classFormButtons = classForm.createButtons();
    }

    private void configureFormListeners() {
        classForm.addSaveAction(this::saveClassAction);
        classForm.addDeleteAction(this::deleteClassAction);
        classForm.addCancelAction(ignored -> cleanAndCloseDialog(addClassDialog));
    }

    private void configureFormDialogs() {
        createFormDialogs();
        configureDialogsListeners();
    }

    private void createFormDialogs() {
        addClassDialog = new FormDialog(classForm, classForm.createButtons(), "Add class to schedule");
    }

    private void configureDialogsListeners() {
        addClassDialog.addDialogCloseActionListener(ignored -> cleanAndCloseDialog(addClassDialog));
    }

    @Override
    protected void presetClass(Class newClassEntity) {
        super.presetClass(newClassEntity);
        classGroupService.findById(classGroupId).ifPresent(newClassEntity::setClassGroup);
    }

    @Override
    public void setParameter(BeforeEvent event, Long classGroupId) {
        this.classGroupId = classGroupId;
        setEntryProvider();
    }

    private void setEntryProvider() {
        // implementation of AbstractEntryProvider was leading to a multiple db calls, probably a bug in the library
        entryProvider = new InMemoryEntryProvider<>(eventsService.makeClassGroupEntries(classGroupId, isUserAdmin));
        calendar.setEntryProvider(entryProvider);
        calendar.getEntryProvider().refreshAll();
    }
}
