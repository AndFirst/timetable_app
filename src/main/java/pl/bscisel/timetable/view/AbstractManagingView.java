package pl.bscisel.timetable.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import jakarta.annotation.PostConstruct;
import pl.bscisel.timetable.data.entity.AbstractEntity;
import pl.bscisel.timetable.form.AbstractForm;
import pl.bscisel.timetable.service.BasicEntityOperationsService;

public abstract class AbstractManagingView<Entity extends AbstractEntity,
        Form extends AbstractForm<Entity>,
        Service extends BasicEntityOperationsService<Entity>> extends VerticalLayout {
    public TextField textFilter;
    public Button addEntityButton;
    public Grid<Entity> grid;

    public Form form;
    public Service service;

    abstract void setForm(Form form);

    abstract void setService(Service service);

    abstract void setGridColumns();

    @PostConstruct
    void init() {
        configureLayout();
        configureToolbar();
        configureGrid();
        setGridColumns();
        configureForm();

        add(createToolbarLayout(), createContentLayout());
        updateItems();
    }

    void configureLayout() {
        setSizeFull();
        addClassName("managing-view");
    }

    void configureToolbar() {
        configureFilter();
        configureAddButton();
    }

    void configureFilter() {
        textFilter = new TextField();
        textFilter.addValueChangeListener(event -> updateItems());
        textFilter.setValueChangeMode(ValueChangeMode.LAZY);
        textFilter.setClearButtonVisible(true);
    }

    void configureAddButton() {
        addEntityButton = new Button();
        addEntityButton.addClickListener(event -> add());
    }

    void configureForm() {
        form.setVisible(false);

        form.addSaveAction(this::save);
        form.addDeleteAction(this::delete);
        form.addCancelAction(ignore -> closeForm());
    }

    void configureGrid() {
        grid = new Grid<>(service.getEntityClass());
        grid.addClassName("grid");
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(event -> edit(event.getValue(), false));
    }

    void save(Entity entity) {
        service.save(entity);
        updateItems();
        closeEditor();
    }

    void delete(Entity entity) {
        service.delete(entity);
        updateItems();
        closeEditor();
    }

    void add() {
        grid.asSingleSelect().clear();
        edit(service.createEmpty(), true);
    }

    void edit(Entity entity, boolean isNew) {
        if (entity == null) {
            closeEditor();
        } else {
            form.setFormBean(entity);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void closeEditor() {
        form.setFormBean(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    void closeForm() {
        closeEditor();
    }

    HorizontalLayout createToolbarLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addClassName("toolbar");
        layout.add(textFilter, addEntityButton);
        return layout;
    }

    HorizontalLayout createContentLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        layout.setFlexGrow(2, grid);
        layout.setFlexGrow(1, form);
        layout.add(grid, form);
        return layout;
    }

    void updateItems() {
        grid.setItems(service.search(textFilter.getValue()));
    }

}

