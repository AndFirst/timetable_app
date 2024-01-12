package pl.bscisel.timetable.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.form.CourseForm;
import pl.bscisel.timetable.service.CourseService;
import pl.bscisel.timetable.view.layout.MainLayout;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "courses", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Courses")
public class CourseManagingView extends AbstractManagingView<Course, CourseForm, CourseService> {

    public CourseManagingView() {
    }

    @Override
    @Autowired
    public void setForm(CourseForm form) {
        this.form = form;
    }

    @Override
    @Autowired
    public void setService(CourseService courseService) {
        this.service = courseService;
    }

    @Override
    public void configureFilter() {
        super.configureFilter();
        textFilter.setPlaceholder("Filter by code or name...");
    }

    @Override
    void configureAddButton() {
        super.configureAddButton();
        addEntityButton.setText("Add course");
    }

    @Override
    public void setGridColumns() {
        grid.setColumns("code", "name", "description");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

}
