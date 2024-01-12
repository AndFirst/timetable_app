package pl.bscisel.timetable.form;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
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

    /**
     * Creates a new consultation form.
     */
    public ConsultationForm() {
        super(new BeanValidationBinder<>(Consultation.class));
    }

    /**
     * Sets the teacherInfoService to be used by this form. Should be automatically autowired by Spring.
     *
     * @param teacherInfoService the teacherInfoService to be set
     */
    @Autowired
    public void setTeacherInfoService(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }

    @Override
    protected void populateFields() {
        super.populateFields();

        teacher.setItems(teacherInfoService.findAll());
        teacher.setItemLabelGenerator(teacher -> teacher.getFullName() + " (#" + teacher.getId() + ")");
    }

    @Override
    protected void setFieldsRequired() {
        super.setFieldsRequired();
        teacher.setRequired(true);
    }

    @Override
    protected void setBindings() {
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
    protected void configureEnterShortcut() {
        configureEnterShortcutWithFix(description);
    }

    @Override
    protected void addComponentsToForm() {
        add(dayOfWeek, createDurationFields(), teacher, location, description);
    }

    /**
     * Sets the bean to be edited in this form. Additionally, sets the teacher field to be disabled if the bean has teacher set.
     *
     * @param bean the bean to be set
     */
    @Override
    public void setFormBean(Consultation bean) {
        super.setFormBean(bean);
        teacher.setEnabled(bean == null || bean.getTeacher() == null);
    }

}
