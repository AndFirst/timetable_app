package pl.bscisel.timetable.views.course;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.views.MainLayout;
import pl.bscisel.timetable.views.course.forms.CourseForm;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "courses", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Courses")
public class CourseView extends VerticalLayout {
    TextField textFilter = new TextField();
    Button addCourseBtn = new Button("Add course", event -> addCourse());
    Grid<Course> grid = new Grid<>(Course.class);
    CourseForm form;
    CourseService courseService;

    public CourseView(CourseService courseService,
                      CourseForm form) {
        this.courseService = courseService;
        this.form = form;
        setSizeFull();

        configureToolbar();
        configureGrid();
        configureForm();

        add(createToolbar(), createContent());
        updateItems();
    }


    void closeForm() {
        closeEditor();
    }

    void saveCourse(Course course) {
        courseService.save(course);
        updateItems();
        closeEditor();
    }

    void deleteCourse(Course course) {
        courseService.delete(course);
        updateItems();
        closeEditor();
    }

    private void configureForm() {
        form.setVisible(false);

        form.addSaveAction(this::saveCourse);
        form.addDeleteAction(this::deleteCourse);
        form.addCancelAction(ignore -> closeForm());
    }

    void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("code", "name", "description");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editCourse(event.getValue()));
    }

    void configureToolbar() {
        textFilter.addValueChangeListener(event -> updateItems());
//        textFilter.setValueChangeMode(ValueChangeMode.LAZY);
        textFilter.setClearButtonVisible(true);
        textFilter.setPlaceholder("Filter by code or name...");
    }

    Component createToolbar() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.add(textFilter, addCourseBtn);
        return layout;
    }

    Component createContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, form);
        layout.add(grid, form);
        return layout;
    }

    void closeEditor() {
        form.setFormBean(null);
        form.setVisible(false);
    }

    void editCourse(Course course) {
        if (course == null) {
            closeEditor();
        } else {
            form.setFormBean(course);
            form.setVisible(true);
        }
    }

    void addCourse() {
        grid.asSingleSelect().clear();
        editCourse(new Course());
    }

    void updateItems() {
        grid.setItems(courseService.search(textFilter.getValue()));
    }
}
