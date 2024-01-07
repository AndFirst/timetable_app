package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import jakarta.annotation.PostConstruct;
import org.atmosphere.config.service.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Class;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

import java.time.DayOfWeek;
import java.time.LocalTime;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClassForm extends AbstractEventForm<Class> {

    ComboBox<Course> course = new ComboBox<>("Course");
    ComboBox<ClassGroup> classGroup = new ComboBox<>("Class group");
    MultiSelectComboBox<TeacherInfo> teachers = new MultiSelectComboBox<>("Teachers");
    Select<Class.ClassFrequency> frequency = new Select<>();
    TextField type = new TextField("Type");
    TextField location = new TextField("Location");
    TextArea description = new TextArea("Description");

    CourseService courseService;
    ClassGroupService classGroupService;
    TeacherInfoService teacherInfoService;


    public ClassForm() {
        super(new BeanValidationBinder<>(Class.class));
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    public void setClassGroupService(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @Autowired
    public void setTeacherInfoService(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }

    @Override
    @PostConstruct
    public void init() {
        super.init();

        configureEnterShortcut(description);
        add(course, classGroup, createFrequencyAndDayFields(), createDurationFields(), teachers, type, location, description);
    }

    @NotNull
    private HorizontalLayout createFrequencyAndDayFields() {
        HorizontalLayout frequencyAndDay = new HorizontalLayout(dayOfWeek, frequency);
        frequencyAndDay.setFlexGrow(1, dayOfWeek, frequency);
        frequencyAndDay.setWidthFull();
        return frequencyAndDay;
    }

    @NotNull
    private HorizontalLayout createDurationFields() {
        HorizontalLayout duration = new HorizontalLayout(startTime, endTime);
        duration.setFlexGrow(1, startTime, endTime);
        duration.setWidthFull();
        return duration;
    }

    @Override
    void populateFields() {
        super.populateFields();

        frequency.setItems(Class.ClassFrequency.values());
        frequency.setItemLabelGenerator(Class.ClassFrequency::getLabel);

        course.setItems(courseService.findAll());
        course.setItemLabelGenerator(course -> course.getCode() + " - " + course.getName());

        classGroup.setItems(classGroupService.findAll());
        classGroup.setItemLabelGenerator(classGroup -> classGroup.getName() + " (#" + classGroup.getId() + ")");

        teachers.setItems(teacherInfoService.findAll());
        teachers.setItemLabelGenerator(teacherInfo -> teacherInfo.getFullName() + " (#" + teacherInfo.getId() + ")");
    }

    @Override
    void configureFields() {
        super.configureFields();
        frequency.setLabel("Frequency");
        frequency.setRequiredIndicatorVisible(true);
    }

    @Override
    public void setFormBean(Class bean) {
        super.setFormBean(bean);
        if (bean != null && bean.getClassGroup() != null) {
            classGroup.setEnabled(false);
        }
    }

    @Override
    void setFieldsRequired() {
        super.setFieldsRequired();
        course.setRequired(true);
        classGroup.setRequired(true);
    }

    @Override
    void setBindings() {
        super.setBindings();

        binder.forField(course)
                .withValidator(new BeanValidator(Class.class, "course"))
                .bind(Class::getCourse, Class::setCourse);

        binder.forField(classGroup)
                .withValidator(new BeanValidator(Class.class, "classGroup"))
                .bind(Class::getClassGroup, Class::setClassGroup);

        binder.forField(frequency)
                .withValidator(new BeanValidator(Class.class, "frequency"))
                .bind(Class::getFrequency, Class::setFrequency);

        binder.forField(teachers)
                .withValidator(new BeanValidator(Class.class, "teachers"))
                .bind(Class::getTeachers, Class::setTeachers);

        binder.forField(type)
                .withValidator(new BeanValidator(Class.class, "type"))
                .bind(Class::getType, Class::setType);

        binder.forField(location)
                .withValidator(new BeanValidator(Class.class, "location"))
                .bind(Class::getLocation, Class::setLocation);

        binder.forField(description)
                .withValidator(new BeanValidator(Class.class, "description"))
                .bind(Class::getDescription, Class::setDescription);
    }

    Binder<Class> getBinder() {
        return binder;
    }
}
