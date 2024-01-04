package pl.bscisel.timetable.views.course.validators;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;

public class CodeExistsValidator extends AbstractValidator<String> {

    private final CourseService courseService;
    private final Binder<Course> binder;

    public CodeExistsValidator(CourseService courseService, Binder<Course> binder) {
        super("{0}");
        this.courseService = courseService;
        this.binder = binder;
    }

    @Override
    public ValidationResult apply(String value, ValueContext context) {
        value = StringUtils.stripToNull(value);
        if (value == null) {
            return ValidationResult.ok();
        }
        boolean codeExists = courseService.existsByCode(value, binder.getBean() == null ? null : binder.getBean().getId());
        return codeExists ? ValidationResult.error(getMessage("Course with this code already exists")) : ValidationResult.ok();
    }
}
