package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
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
public class ClassForm extends AbstractForm<Class> {

    ComboBox<Course> course = new ComboBox<>("Course");
    ComboBox<ClassGroup> classGroup = new ComboBox<>("Class group");
    MultiSelectComboBox<TeacherInfo> teachers = new MultiSelectComboBox<>("Teachers");
    Select<DayOfWeek> dayOfWeek = new Select<>();
    Select<Class.ClassFrequency> frequency = new Select<>();
    TimePicker startTime = new TimePicker("Start time");
    TimePicker endTime = new TimePicker("End time");
    TextField type = new TextField("Type");
    TextField location = new TextField("Location");
    TextArea description = new TextArea("Description");
    CourseService courseService;
    ClassGroupService classGroupService;
    TeacherInfoService teacherInfoService;


    public ClassForm(CourseService courseService,
                     ClassGroupService classGroupService,
                     TeacherInfoService teacherInfoService) {
        super(new BeanValidationBinder<>(Class.class));
        this.courseService = courseService;
        this.classGroupService = classGroupService;
        this.teacherInfoService = teacherInfoService;

        setResponsiveSteps(new ResponsiveStep("0", 1));

        HorizontalLayout duration = new HorizontalLayout(startTime, endTime);
        duration.setFlexGrow(1, startTime, endTime);
        duration.setWidthFull();

        HorizontalLayout frequencyAndDay = new HorizontalLayout(dayOfWeek, frequency);
        frequencyAndDay.setFlexGrow(1, dayOfWeek, frequency);
        frequencyAndDay.setWidthFull();

        setFieldsRequired();
        populateFields();
        setBindings();
        configureEnterShortcut(description);

        add(course, classGroup, frequencyAndDay, duration, teachers, type, location, description);
    }

    private void setBindings() {
        binder.forField(course)
                .withValidator(new BeanValidator(Class.class, "course"))
                .bind(Class::getCourse, Class::setCourse);
        binder.forField(classGroup)
                .withValidator(new BeanValidator(Class.class, "classGroup"))
                .bind(Class::getClassGroup, Class::setClassGroup);
        binder.forField(dayOfWeek)
                .withValidator(new BeanValidator(Class.class, "dayOfWeek"))
                .bind(Class::getDayOfWeek, Class::setDayOfWeek);
        binder.forField(frequency)
                .withValidator(new BeanValidator(Class.class, "frequency"))
                .bind(Class::getFrequency, Class::setFrequency);
        binder.forField(startTime)
                .withValidator(new BeanValidator(Class.class, "startTime"))
                .withValidator(startTime -> {
                    if (binder.getBean() == null || binder.getBean().getEndTime() == null)
                        return true;
                    return startTime.isBefore(binder.getBean().getEndTime());
                }, "Start time must be before end time")
                .bind(Class::getStartTime, Class::setStartTime);
        binder.forField(endTime)
                .withValidator(new BeanValidator(Class.class, "endTime"))
                .withValidator(endTime -> {
                    if (binder.getBean() == null || binder.getBean().getStartTime() == null)
                        return true;
                    return endTime.isAfter(binder.getBean().getStartTime());
                }, "End time must be after start time")
                .withValidator(endTime -> {
                    if (binder.getBean() == null || binder.getBean().getStartTime() == null)
                        return true;
                    return endTime.minusMinutes(14).isAfter(binder.getBean().getStartTime());
                }, "Class must be at least 15 minutes long")
                .bind(Class::getEndTime, Class::setEndTime);

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

    private void populateFields() {
        frequency.setLabel("Frequency");
        frequency.setItems(Class.ClassFrequency.values());
        frequency.setItemLabelGenerator(Class.ClassFrequency::getLabel);
        frequency.setRequiredIndicatorVisible(true);

        dayOfWeek.setLabel("Day of week");
        dayOfWeek.setItems(DayOfWeek.values());
        dayOfWeek.setItemLabelGenerator(item -> item.toString().charAt(0) + item.toString().substring(1).toLowerCase());
        dayOfWeek.setRequiredIndicatorVisible(true);

        course.setItems(courseService.findAll());
        course.setItemLabelGenerator(course -> course.getCode() + " - " + course.getName());

        classGroup.setItems(classGroupService.findAll());

        classGroup.setItemLabelGenerator(classGroup -> classGroup.getName() + " (#" + classGroup.getId() + ")");

        startTime.setMin(LocalTime.of(7, 0));
        startTime.setMax(LocalTime.of(22, 22));

        endTime.setMin(LocalTime.of(7, 0));
        endTime.setMax(LocalTime.of(22, 22));

        teachers.setItems(teacherInfoService.findAll());
        teachers.setItemLabelGenerator(teacherInfo -> teacherInfo.getFullName() + " (#" + teacherInfo.getId() + ")");
    }

    @Override
    public void setFormBean(Class bean) {
        super.setFormBean(bean);
        if (bean != null && bean.getClassGroup() != null) {
            classGroup.setEnabled(false);
        }
    }

    private void setFieldsRequired() {
        course.setRequired(true);
        classGroup.setRequired(true);
        startTime.setRequired(true);
        endTime.setRequired(true);
    }
}
