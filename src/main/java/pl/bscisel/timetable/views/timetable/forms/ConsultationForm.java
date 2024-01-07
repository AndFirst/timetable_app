package pl.bscisel.timetable.views.timetable.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ConsultationForm extends AbstractEventForm<Consultation> {

    ComboBox<TeacherInfo> teacher = new ComboBox<>("Teacher");
    TextField location = new TextField("Location");
    TextArea description = new TextArea("Description");
    TeacherInfoService teacherInfoService;

    public ConsultationForm() {
        super(new BeanValidationBinder<>(Consultation.class));
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
        add(dayOfWeek, createDurationFields(), teacher, location, description);
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

        teacher.setItems(teacherInfoService.findAll());
        teacher.setItemLabelGenerator(teacher -> teacher.getFullName() + " (#" + teacher.getId() + ")");
    }

    @Override
    public void setFormBean(Consultation bean) {
        super.setFormBean(bean);
        if (bean != null && bean.getTeacher() != null) {
            teacher.setEnabled(false);
        }
    }

    @Override
    void setFieldsRequired() {
        super.setFieldsRequired();
        teacher.setRequired(true);
    }

    @Override
    void setBindings() {
        super.setBindings();

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

    Binder<Consultation> getBinder() {
        return binder;
    }
}
