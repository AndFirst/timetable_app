package pl.bscisel.timetable.views.users;

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
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.UserService;
import pl.bscisel.timetable.views.MainLayout;


@Route(value = "users", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Users")
public class UsersView extends VerticalLayout {
    TextField textFilter = new TextField();
    Grid<User> grid = new Grid<>(User.class);
    UserForm form;
    Button addUserBtn = new Button("Add user", event -> addUser());
    UserService userService;

    public UsersView(UserService userService,
                     UserForm form) {
        this.userService = userService;
        this.form = form;
        setSizeFull();

        configureToolbar();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateItems();
    }


    private void closeForm() {
        closeEditor();
    }

    private void saveUser(User user) {
        userService.save(user);
        updateItems();
        closeEditor();
    }

    private void deleteUser(User user) {
        userService.delete(user);
        updateItems();
        closeEditor();
    }

    private void configureForm() {
        form.setVisible(false);

        form.addSaveAction(this::saveUser);
        form.addDeleteAction(this::deleteUser);
        form.addCancelAction(ignore -> closeForm());
    }

    void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("emailAddress");
        grid.addColumn(User::formatRoles).setHeader("Roles");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue(), false));
    }

    void configureToolbar() {
        textFilter.addValueChangeListener(event -> updateItems());
        textFilter.setValueChangeMode(ValueChangeMode.LAZY);
        textFilter.setClearButtonVisible(true);
        textFilter.setPlaceholder("Filter by email address...");
    }

    Component getToolbar() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.add(textFilter, addUserBtn);
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
        form.setFormBean(null);
        form.setVisible(false);
    }

    private void editUser(User user, boolean newUser) {
        if (user == null) {
            closeEditor();
        } else {
            if (newUser)
                form.setMode(UserForm.Mode.ADD);
            else
                form.setMode(UserForm.Mode.EDIT);
            form.setFormBean(user);
            form.setVisible(true);
        }
    }

    private void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User(), true);
    }

    void updateItems() {
        grid.setItems(userService.search(textFilter.getValue()));
    }
}
