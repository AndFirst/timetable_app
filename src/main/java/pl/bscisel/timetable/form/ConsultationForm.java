package pl.bscisel.timetable.form;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Consultation;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.service.TeacherInfoService;

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
    void populateFields() {
        super.populateFields();

        teacher.setItems(teacherInfoService.findAll());
        teacher.setItemLabelGenerator(teacher -> teacher.getFullName() + " (#" + teacher.getId() + ")");
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

    @Override
    void configureEnterShortcut() {
        configureEnterShortcutWithFix(description);
    }

    @Override
    void addComponentsToForm() {
        add(dayOfWeek, createDurationFields(), teacher, location, description);
    }

    @Override
    public void setFormBean(Consultation bean) {
        super.setFormBean(bean);
        teacher.setEnabled(bean == null || bean.getTeacher() == null);
    }

    Binder<Consultation> getBinder() {
        return binder;
    }
}
