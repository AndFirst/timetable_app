package pl.bscisel.timetable.view.timetables;

import com.vaadin.flow.component.Component;
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
import pl.bscisel.timetable.data.entity.AbstractEntity;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.form.ClassForm;
import pl.bscisel.timetable.form.ConsultationForm;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.CourseService;
import pl.bscisel.timetable.service.EventsService;
import pl.bscisel.timetable.service.TeacherInfoService;
import pl.bscisel.timetable.view.layout.MainLayout;
import pl.bscisel.timetable.view.timetables.components.FormDialog;
import pl.bscisel.timetable.view.timetables.components.TimetableEntry;

import java.util.Optional;

// LLR_100, LLR_210
@PageTitle("Teacher")
@Route(value = "teacher", layout = MainLayout.class)
@AnonymousAllowed
public class TeacherTimetableView extends AbstractClassTimetableView implements HasUrlParameter<Long> {
    private static final Logger logger = LoggerFactory.getLogger(TeacherTimetableView.class);

    private final TeacherInfoService teacherInfoService;
    private final SecurityService securityService;
    private final CourseService courseService;
    private final ClassGroupService classGroupService;

    private boolean isUserAdmin;
    private Long viewTeacherId;
    private Optional<Long> loggedTeacherId;
    private boolean isLoggedUserViewingOwnTimetable;

    private HorizontalLayout toolbar;
    private Button addConsultationBtn;
    private Button addClassBtn;

    private FormDialog addConsultationDialog;
    private ConsultationForm consultationForm;
    private Component consultationFormButtons;

    private Registration entryClickedRegistration;

    private ApplicationContext applicationContext;

    public TeacherTimetableView(EventsService eventsService,
                                TeacherInfoService teacherInfoService,
                                SecurityService securityService,
                                CourseService courseService,
                                ClassGroupService classGroupService,
                                ApplicationContext applicationContext) {
        super(eventsService);
        logger.debug("TeacherView constructor");
        this.teacherInfoService = teacherInfoService;
        this.securityService = securityService;
        this.courseService = courseService;
        this.classGroupService = classGroupService;
        this.applicationContext = applicationContext;
        this.isUserAdmin = securityService.isUserAdmin();
        this.loggedTeacherId = loggedTeacherId();

        if (isUserAdmin) {
            configureCalendarListeners();
            setToolbar();
        }

        if (isUserAdmin || securityService.isUserTeacher()) {
            configureForm();
            configureFormDialogs();
        }
    }


    private void configureFormDialogs() {
        createFormDialogs();
        configureDialogsListeners();
    }

    private void createFormDialogs() {
        addClassDialog = new FormDialog(classForm, classFormButtons, "Add class");
        addConsultationDialog = new FormDialog(consultationForm, consultationFormButtons, "Add consultation");
    }

    private void configureDialogsListeners() {
        addClassDialog.addDialogCloseActionListener(ignored -> cleanAndCloseDialog(addClassDialog));
        addConsultationDialog.addDialogCloseActionListener(ignored -> cleanAndCloseDialog(addConsultationDialog));
    }

    private void configureForm() {
        createForms();
        configureFormListeners();
    }

    private void createForms() {
        logger.debug("createForms");
        classForm = applicationContext.getBean(ClassForm.class);
        classFormButtons = classForm.createButtons();
        consultationForm = applicationContext.getBean(ConsultationForm.class);
        consultationFormButtons = consultationForm.createButtons();
    }

    private void configureFormListeners() {
        classForm.addSaveAction(this::saveClassAction);
        classForm.addDeleteAction(this::deleteClassAction);
        classForm.addCancelAction(ignored -> cleanAndCloseDialog(addClassDialog));

        consultationForm.addSaveAction(this::saveConsultationAction);
        consultationForm.addDeleteAction(this::deleteConsultationAction);
        consultationForm.addCancelAction(ignored -> cleanAndCloseDialog(addConsultationDialog));
    }

    private void deleteConsultationAction(Consultation consultation) {
        logger.debug("deleteConsultationAction");
        eventsService.delete(consultation);
        removeEditedEntry();
        entryProvider.refreshAll();
        cleanAndCloseDialog(addConsultationDialog);
    }

    private void saveConsultationAction(Consultation consultation) {
        logger.debug("saveConsultationAction");
        eventsService.save(consultation);
        removeEditedEntry();
        entryProvider.addEntry(eventsService.makeEntry(consultation, true));
        entryProvider.refreshAll();
        cleanAndCloseDialog(addConsultationDialog);
    }

    @Override
    protected void cleanAfterEditing() {
        super.cleanAfterEditing();
        consultationForm.setFormBean(null);
    }

    private Optional<Long> loggedTeacherId() {
        Account authenticatedAccount = securityService.getTimetableAccountOfAuthenticatedTeacher();
        if (authenticatedAccount == null) {
            return Optional.empty();
        }
        return teacherInfoService.findByAccountId(authenticatedAccount.getId())
                .map(AbstractEntity::getId);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long teacherId) {
        if (this.viewTeacherId != null && this.viewTeacherId.equals(teacherId)) {
            return;
        }
        logger.debug("Teacher view set teacherId: {}", teacherId);
        this.viewTeacherId = teacherId;
        isLoggedUserViewingOwnTimetable = loggedTeacherId.map(id -> id.equals(teacherId)).orElse(false);
        setListenersForMovingAndResizing(isUserAdmin || isLoggedUserViewingOwnTimetable);
        if (!isUserAdmin && isLoggedUserViewingOwnTimetable) {
            configureCalendarListeners();
            setToolbar();
        } else if (!isUserAdmin) {
            removeCalendarListeners();
            removeToolbar();
        }
        setEntryProvider();
    }

    private void removeCalendarListeners() {
        if (entryClickedRegistration != null) {
            entryClickedRegistration.remove();
            entryClickedRegistration = null;
        }

        if (slotsSelectedListener != null) {
            slotsSelectedListener.remove();
            slotsSelectedListener = null;
        }
    }

    private void configureCalendarListeners() {
        configureTimeslotsListeners();
        configureEntryClickedListener();
    }

    private void configureEntryClickedListener() {
        if (entryClickedRegistration != null) {
            return;
        }
        entryClickedRegistration = calendar.addEntryClickedListener(event -> {
            logger.debug("Entry clicked");
            editedEntry = (TimetableEntry) event.getEntry();
            if (editedEntry.isEditable() && editedEntry.getEvent() instanceof Class) {
                editClass((Class) editedEntry.getEvent());
            } else if (editedEntry.isEditable() && editedEntry.getEvent() instanceof Consultation) {
                editConsultation((Consultation) editedEntry.getEvent());
            }
        });
    }

    // LLR_214, LLR_215
    private void editConsultation(Consultation event) {
        consultationForm.setFormBean(event);
        addConsultationDialog.open();
    }

    private void setToolbar() {
        addToolbar();
        populateToolbar();
    }

    private void removeToolbar() {
        if (toolbar != null) {
            remove(toolbar);
            toolbar = null;
            logger.debug("Toolbar removed");
        }
    }

    private void addToolbar() {
        if (toolbar == null) {
            toolbar = new HorizontalLayout();
            addComponentAsFirst(toolbar);
            logger.debug("Toolbar added");
        }
    }

    private void populateToolbar() {
        if (toolbar == null) {
            throw new IllegalStateException("Toolbar is null");
        }
        if (isUserAdmin || isLoggedUserViewingOwnTimetable) {
            addConsultationBtn = new Button("Add teacher consultation");
            addConsultationBtn.addClickListener(ignored -> addNewConsultation());
            toolbar.add(addConsultationBtn);
        }
        if (isUserAdmin) {
            addClassBtn = new Button("Add class");
            addClassBtn.addClickListener(ignored -> addNewClass());
            toolbar.add(addClassBtn);
        }
        logger.debug("Toolbar populated");
    }

    // LLR_212
    private void addNewConsultation() {
        logger.debug("Adding new consultation");
        Consultation consultation = new Consultation();
        teacherInfoService.findById(viewTeacherId).ifPresent(consultation::setTeacher);
        setSelectedTimeslotsData(consultation);
        editConsultation(consultation);
    }

    // LLR_213
    private void setEntryProvider() {
        // implementation of AbstractEntryProvider was leading to a multiple db calls, probably a bug in the library
        entryProvider = new InMemoryEntryProvider<>();
        entryProvider.addEntries(eventsService.makeTeacherClassesEntries(viewTeacherId, isUserAdmin));
        entryProvider.addEntries(eventsService.makeTeacherConsultationsEntries(viewTeacherId, isUserAdmin || isLoggedUserViewingOwnTimetable));
        calendar.setEntryProvider(entryProvider);
        calendar.getEntryProvider().refreshAll();
    }
}
