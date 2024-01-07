package pl.bscisel.timetable.view.organizationalunits;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalConfigurableFilterDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.jetbrains.annotations.Nullable;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.OrganizationalUnitService;
import pl.bscisel.timetable.view.layout.MainLayout;
import pl.bscisel.timetable.view.organizationalunits.dataproviders.OrganizationalUnitDataProvider;
import pl.bscisel.timetable.form.ClassGroupForm;
import pl.bscisel.timetable.form.OrganizationalUnitForm;

import java.util.LinkedList;


@Route(value = "organizationalunits", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Organizational units")
public class OrganizationalUnitView extends VerticalLayout {
    TreeGrid<OrganizationalUnit> unitsGrid = new TreeGrid<>(OrganizationalUnit.class);
    Grid<ClassGroup> groupsGrid = new Grid<>(ClassGroup.class);
    OrganizationalUnitService unitService;
    ClassGroupService groupsService;
    HierarchicalConfigurableFilterDataProvider<OrganizationalUnit, Void, String> dataProvider;
    OrganizationalUnitForm unitForm;
    ClassGroupForm groupForm;

    /**
     * Toolbar buttons.
     */
    Button addUnitBtn = new Button("Add organizational unit");
    Button addGroupBtn = new Button("Add class group");
    Button editUnitBtn = new Button("Edit organizational unit");
    Button editGroupBtn = new Button("Edit class group");

    public OrganizationalUnitView(OrganizationalUnitService organizationalUnitService,
                                  OrganizationalUnitDataProvider dataProvider,
                                  OrganizationalUnitForm organizationalUnitForm,
                                  ClassGroupService classGroupService,
                                  ClassGroupForm classGroupForm) {
        this.unitService = organizationalUnitService;
        this.groupsService = classGroupService;
        this.dataProvider = dataProvider.withConfigurableFilter();
        this.unitForm = organizationalUnitForm;
        this.groupForm = classGroupForm;
        setSizeFull();

        configureButtons();
        configureUnitsGrid();
        configureGroupsGrid();
        configureUnitForm();
        configureGroupForm();

        add(getToolbar(), getContent());
    }

    private void addUnit() {
        OrganizationalUnit newUnit = new OrganizationalUnit();
        // if some organizational unit is selected, then set it as parent unit
        if (unitsGrid.asSingleSelect().getValue() != null) {
            newUnit.setParentUnit(unitsGrid.asSingleSelect().getValue());
        }
        editUnit(newUnit);
    }

    private void editUnit(@Nullable OrganizationalUnit organizationalUnit) {
        if (organizationalUnit == null) {
            closeUnitEditor();
        } else {
            closeGroupEditor();
            unitForm.setFormBean(organizationalUnit.clone());
            unitForm.setVisible(true);
        }
    }

    void closeUnitEditor() {
        unitForm.setFormBean(null);
        unitForm.setVisible(false);
    }

    private void addGroup() {
        groupsGrid.asSingleSelect().clear();
        ClassGroup newClassGroup = new ClassGroup();
        newClassGroup.setOrganizationalUnit(unitsGrid.asSingleSelect().getValue());
        editGroup(newClassGroup);
    }

    private void editGroup(@Nullable ClassGroup classGroup) {
        if (classGroup == null) {
            closeGroupEditor();
        } else {
            closeUnitEditor();
            groupForm.setFormBean(classGroup);
            groupForm.setVisible(true);
        }
    }

    void closeGroupEditor() {
        groupForm.setFormBean(null);
        groupForm.setVisible(false);
    }

    private void configureButtons() {
        addUnitBtn.setEnabled(true);
        addUnitBtn.addClickListener(event -> addUnit());

        addGroupBtn.setEnabled(true);
        addGroupBtn.addClickListener(event -> addGroup());

        editUnitBtn.setEnabled(false);
        editUnitBtn.addClickListener(event -> editUnit(unitsGrid.asSingleSelect().getValue()));

        editGroupBtn.setEnabled(false);
        editGroupBtn.addClickListener(event -> editGroup(groupsGrid.asSingleSelect().getValue()));
    }

    private void configureUnitForm() {
        unitForm.setVisible(false);
        unitForm.addSaveAction(this::saveUnit);
        unitForm.addDeleteAction(this::deleteUnit);
        unitForm.addCancelAction(ignore -> closeUnitEditor());
    }

    void saveUnit(@Nullable OrganizationalUnit unit) {
        if (unit != null) {
            unitService.save(unit);
        }
        finishUnitEditing(unit);
        selectUnitInGrid(unit);
    }

    void deleteUnit(@Nullable OrganizationalUnit unit) {
        if (unit != null) {
            unitService.delete(unit);
        }
        updateGroupGrid(null);
        finishUnitEditing(unit);
    }

    void finishUnitEditing(@Nullable OrganizationalUnit organizationalUnit) {
        dataProvider.refreshAll();
        if (organizationalUnit != null) {
            expandParents(organizationalUnit.getParentUnit());
        }
        closeUnitEditor();
    }

    private void configureGroupForm() {
        groupForm.setVisible(false);
        groupForm.addSaveAction(this::saveGroup);
        groupForm.addDeleteAction(this::deleteGroup);
        groupForm.addCancelAction(ignore -> closeGroupEditor());
    }

    void saveGroup(@Nullable ClassGroup classGroup) {
        if (classGroup != null) {
            groupsService.save(classGroup);
        }
        finishGroupEditing(classGroup);
    }

    void deleteGroup(@Nullable ClassGroup classGroup) {
        if (classGroup != null) {
            groupsService.delete(classGroup);
        }
        finishGroupEditing(classGroup);
    }

    void finishGroupEditing(@Nullable ClassGroup classGroup) {
        closeGroupEditor();
        if (classGroup != null) {
            expandParents(classGroup.getOrganizationalUnit());
            selectUnitInGrid(classGroup.getOrganizationalUnit());
            updateGroupGrid(classGroup.getOrganizationalUnit());
        }
    }

    void selectUnitInGrid(@Nullable OrganizationalUnit organizationalUnit) {
        if (organizationalUnit != null) {
            unitsGrid.select(organizationalUnit);
        }
    }

    void expandParents(@Nullable OrganizationalUnit parent) {
        while (parent != null) {
            unitsGrid.expand(parent);
            parent = parent.getParentUnit();
        }
    }

    private Component getToolbar() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.add(addUnitBtn, editUnitBtn, addGroupBtn, editGroupBtn);
        return layout;
    }

    private Component getGrids() {
        VerticalLayout layout = new VerticalLayout(unitsGrid, groupsGrid);
        layout.setPadding(false);
        layout.setSizeFull();
        layout.setFlexShrink(1, unitsGrid);
        layout.setFlexShrink(2, groupsGrid);
        return layout;
    }

    private Component getContent() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSizeFull();
        Component grids = getGrids();
        layout.setFlexGrow(2, grids);
        layout.setFlexGrow(1, unitForm);
        layout.setFlexGrow(1, groupForm);
        layout.add(grids, unitForm, groupForm);
        return layout;
    }

    private void configureUnitsGrid() {
        unitsGrid.setSizeFull();
        unitsGrid.setDataProvider(dataProvider);
        unitsGrid.removeAllColumns();
        unitsGrid.addHierarchyColumn(unitService::getNameWithId).setHeader("Organizational unit name");
        unitsGrid.addColumn(OrganizationalUnit::getDescription).setHeader("Description");

        unitsGrid.getColumns().forEach(col -> col.setAutoWidth(true));

        unitsGrid.asSingleSelect().addValueChangeListener(event -> {
            closeGroupEditor();
            updateGroupGrid(event.getValue());
            editUnitBtn.setEnabled(event.getValue() != null);
        });
    }

    void updateGroupGrid(@Nullable OrganizationalUnit organizationUnit) {
        if (organizationUnit == null || organizationUnit.getId() == null) {
            groupsGrid.setItems(new LinkedList<>());
        } else {
            groupsGrid.setItems(groupsService.findByOrganizationalUnitId(organizationUnit.getId()));
        }
    }

    private void configureGroupsGrid() {
        groupsGrid.setSizeFull();
        groupsGrid.removeAllColumns();
        groupsGrid.addColumn(ClassGroup::getName).setHeader("Class group name");
        groupsGrid.addColumn(ClassGroup::getDescription).setHeader("Description");

        groupsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        groupsGrid.asSingleSelect().addValueChangeListener(event -> {
            closeUnitEditor();
            editGroup(event.getValue());
            editGroupBtn.setEnabled(event.getValue() != null);
        });
    }
}
