package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

import java.time.DayOfWeek;
import java.time.LocalTime;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConsultationForm extends AbstractForm<Consultation> {

    ComboBox<TeacherInfo> teacher = new ComboBox<>("Teacher");
    Select<DayOfWeek> dayOfWeek = new Select<>();
    TimePicker startTime = new TimePicker("Start time");
    TimePicker endTime = new TimePicker("End time");
    TextField location = new TextField("Location");
    TextArea description = new TextArea("Description");
    TeacherInfoService teacherInfoService;


    public ConsultationForm(TeacherInfoService teacherInfoService) {
        super(new BeanValidationBinder<>(Consultation.class));
        this.teacherInfoService = teacherInfoService;

        setResponsiveSteps(new ResponsiveStep("0", 1));

        HorizontalLayout duration = new HorizontalLayout(startTime, endTime);
        duration.setFlexGrow(1, startTime, endTime);
        duration.setWidthFull();

        setFieldsRequired();
        populateFields();
        setBindings();
        configureEnterShortcut(description);

        add(dayOfWeek, duration, teacher, location, description);
    }

    private void setBindings() {
        binder.forField(dayOfWeek)
                .withValidator(new BeanValidator(Consultation.class, "dayOfWeek"))
                .bind(Consultation::getDayOfWeek, Consultation::setDayOfWeek);
        binder.forField(startTime)
                .withValidator(new BeanValidator(Consultation.class, "startTime"))
                .withValidator(startTime -> {
                    if (binder.getBean() == null || binder.getBean().getEndTime() == null)
                        return true;
                    return startTime.isBefore(binder.getBean().getEndTime());
                }, "Start time must be before end time")
                .bind(Consultation::getStartTime, Consultation::setStartTime);
        binder.forField(endTime)
                .withValidator(new BeanValidator(Consultation.class, "endTime"))
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
                .bind(Consultation::getEndTime, Consultation::setEndTime);

        binder.forField(teacher)
                .withValidator(new BeanValidator(Consultation.class, "teacher"))
                .bind(Consultation::getTeacher, Consultation::setTeacher);

        binder.forField(location)
                .withValidator(new BeanValidator(Consultation.class, "location"))
                .bind(Consultation::getLocation, Consultation::setLocation);

        binder.forField(description)
                .withValidator(new BeanValidator(Consultation.class, "description"))
                .bind(Consultation::getDescription, Consultation::setDescription);
    }

    private void populateFields() {
        dayOfWeek.setLabel("Day of week");
        dayOfWeek.setItems(DayOfWeek.values());
        dayOfWeek.setItemLabelGenerator(item -> item.toString().charAt(0) + item.toString().substring(1).toLowerCase());
        dayOfWeek.setRequiredIndicatorVisible(true);

        teacher.setItems(teacherInfoService.findAll());
        teacher.setItemLabelGenerator(teacher -> teacher.getFullName() + " (#" + teacher.getId() + ")");

        startTime.setMin(LocalTime.of(7, 0));
        startTime.setMax(LocalTime.of(22, 22));

        endTime.setMin(LocalTime.of(7, 0));
        endTime.setMax(LocalTime.of(22, 22));
    }

    @Override
    public void setFormBean(Consultation bean) {
        super.setFormBean(bean);
        if (bean != null && bean.getTeacher() != null) {
            teacher.setEnabled(false);
        }
    }

    private void setFieldsRequired() {
        teacher.setRequired(true);
        startTime.setRequired(true);
        endTime.setRequired(true);
    }
}
