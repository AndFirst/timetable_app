package pl.bscisel.timetable.views.teachers;

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
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.views.MainLayout;


@Route(value = "teachers", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Teachers")
public class TeacherInfoView extends VerticalLayout {
    TextField textFilter = new TextField();
    Grid<TeacherInfo> grid = new Grid<>(TeacherInfo.class);
    TeacherInfoForm form;
    Button addTeacherBtn = new Button("Add teacher", event -> addTeacher());
    TeacherInfoService teacherInfoService;

    public TeacherInfoView() {}

    @Autowired
    public void setTeacherInfoService(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }

    @Autowired
    public void setForm(TeacherInfoForm form) {
        this.form = form;
    }

    @PostConstruct
    void init() {
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

    void saveTeacher(TeacherInfo teacher) {
        teacherInfoService.save(teacher);
        updateItems();
        closeEditor();
    }

    void deleteTeacher(TeacherInfo teacher) {
        teacherInfoService.delete(teacher);
        updateItems();
        closeEditor();
    }

    void configureForm() {
        form.setVisible(false);

        form.addSaveAction(this::saveTeacher);
        form.addDeleteAction(this::deleteTeacher);
        form.addCancelAction(ignore -> closeForm());
    }

    void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("id", "degree", "name", "surname", "phoneNumber");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editTeacher(event.getValue()));
    }

    void configureToolbar() {
        textFilter.addValueChangeListener(event -> updateItems());
        textFilter.setValueChangeMode(ValueChangeMode.LAZY);
        textFilter.setClearButtonVisible(true);
        textFilter.setPlaceholder("Filter...");
    }

    Component createToolbar() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.add(textFilter, addTeacherBtn);
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

    void editTeacher(TeacherInfo teacher) {
        if (teacher == null) {
            closeEditor();
        } else {
            form.setFormBean(teacher);
            form.setVisible(true);
        }
    }

    void addTeacher() {
        grid.asSingleSelect().clear();
        editTeacher(new TeacherInfo());
    }

    void updateItems() {
        grid.setItems(teacherInfoService.search(textFilter.getValue()));
    }
}
