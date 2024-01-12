package pl.bscisel.timetable.form;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.service.CourseService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CourseForm extends AbstractForm<Course> {
    TextField code = new TextField("Code");
    TextField name = new TextField("Name");
    TextArea description = new TextArea("Description");

    CourseService courseService;

    /**
     * Creates new course form.
     */
    public CourseForm() {
        super(new BeanValidationBinder<>(Course.class));
    }

    /**
     * Sets course service. Should be automatically autowired by Spring.
     *
     * @param courseService course service
     */
    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    protected void configureForm() {

    }

    @Override
    protected void configureFields() {

    }

    @Override
    protected void setFieldsRequired() {
        code.setRequired(true);
        name.setRequired(true);
    }

    @Override
    protected void populateFields() {

    }

    @Override
    protected void setBindings() {
        binder.forField(code)
                .withValidator(new BeanValidator(Course.class, "code"))
                .withValidator(code -> {
                    if (binder.getBean() == null)
                        return true;
                    return !courseService.existsByCode(code, binder.getBean().getId());
                }, "Course with this code already exists")
                .bind(Course::getCode, Course::setCode);
        binder.forField(name)
                .withValidator(new BeanValidator(Course.class, "name"))
                .bind(Course::getName, Course::setName);
        binder.forField(description)
                .withValidator(new BeanValidator(Course.class, "description"))
                .bind(Course::getDescription, Course::setDescription);
    }

    @Override
    protected void configureEnterShortcut() {
        configureEnterShortcutWithFix(description);
    }

    @Override
    protected void addComponentsToForm() {
        add(code, name, description, createButtons());
    }

}
