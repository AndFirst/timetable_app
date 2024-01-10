package pl.bscisel.timetable.view.courses;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.service.CourseService;
import pl.bscisel.timetable.form.CourseForm;
import pl.bscisel.timetable.view.layout.MainLayout;

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

    public CourseView() {}

    @Autowired
    public void setForm(CourseForm form) {
        this.form = form;
    }

    @Autowired
    public void setCourseService(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostConstruct
    void init() {
        setSizeFull();
        addClassName("managing-view");

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

    void configureForm() {
        form.setVisible(false);

        form.addSaveAction(this::saveCourse);
        form.addDeleteAction(this::deleteCourse);
        form.addCancelAction(ignore -> closeForm());
    }

    void configureGrid() {
        grid.addClassName("grid");
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

    Component createToolbar() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("toolbar");

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
        removeClassName("editing");
    }

    void editCourse(Course course) {
        if (course == null) {
            closeEditor();
        } else {
            form.setFormBean(course);
            form.setVisible(true);
            addClassName("editing");
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
