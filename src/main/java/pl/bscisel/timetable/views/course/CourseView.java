package pl.bscisel.timetable.views.course;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.views.MainLayout;


@Route(value = "courses", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Courses")
public class CourseView extends VerticalLayout {
    TextField textFilter = new TextField();
    Button addCourseBtn = new Button("Add course", event -> addCourse());
    Grid<Course> grid = new Grid<>(Course.class);
    CourseForm form = new CourseForm();
    CourseService courseService;

    public CourseView(CourseService courseService) {
        this.courseService = courseService;
        setSizeFull();

        configureToolbar();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateItems();
    }


    private void closeForm(CourseForm.CloseEvent closeEvent) {
        closeEditor();
    }

    private void saveCourse(CourseForm.SaveEvent saveEvent) {
        courseService.save(saveEvent.getCourse());
        updateItems();
        closeEditor();
    }

    private void deleteCourse(CourseForm.DeleteEvent deleteEvent) {
        courseService.delete(deleteEvent.getCourse());
        updateItems();
        closeEditor();
    }

    private void configureForm() {
        form.setVisible(false);

        form.addSaveListener(this::saveCourse);
        form.addDeleteListener(this::deleteCourse);
        form.addCloseListener(this::closeForm);
    }

    void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("code", "name", "description");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editCourse(event.getValue()));
    }

    void configureToolbar() {
        textFilter.addValueChangeListener(event -> updateItems());
        textFilter.setValueChangeMode(ValueChangeMode.LAZY);
        textFilter.setClearButtonVisible(true);
        textFilter.setPlaceholder("Filter by code or name...");
    }

    Component getToolbar() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.add(textFilter, addCourseBtn);
        return layout;
    }

    Component getContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, form);
        layout.add(grid, form);
        return layout;
    }

    private void closeEditor() {
        form.setCourse(null);
        form.setVisible(false);
    }

    private void editCourse(Course course) {
        if (course == null) {
            closeEditor();
        } else {
            form.setCourse(course);
            form.setVisible(true);
        }
    }

    private void addCourse() {
        grid.asSingleSelect().clear();
        editCourse(new Course());
    }

    void updateItems() {
        grid.setItems(courseService.search(textFilter.getValue()));
    }
}
