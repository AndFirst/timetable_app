package pl.bscisel.timetable.views.course;

import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CourseForm extends AbstractForm<Course> {
    TextField code = new TextField("Code");
    TextField name = new TextField("Name");
    TextArea description = new TextArea("Description");

    CourseService courseService;

    public CourseForm(CourseService courseService) {
        super(new BeanValidationBinder<>(Course.class));
        this.courseService = courseService;

        setRequiredFields();
        setBindings();
        configureEnterShortcut(description);

        add(code, name, description, getButtons());
    }

    private void setRequiredFields() {
        code.setRequired(true);
        name.setRequired(true);
    }

    private void setBindings() {
        binder.forField(code)
                .withValidator(new BeanValidator(Course.class, "code"))
                .withValidator(code -> {
                    if (binder.getBean() == null)
                        return true;
                    return !courseService.courseExistsByCode(code, binder.getBean().getId());
                }, "Course with this code already exists")
                .bind(Course::getCode, Course::setCode);
        binder.forField(name)
                .withValidator(new BeanValidator(Course.class, "name"))
                .bind(Course::getName, Course::setName);
        binder.forField(description)
                .withValidator(new BeanValidator(Course.class, "description"))
                .bind(Course::getDescription, Course::setDescription);
    }

}