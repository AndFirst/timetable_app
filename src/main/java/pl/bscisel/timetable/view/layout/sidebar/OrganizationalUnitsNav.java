package pl.bscisel.timetable.view.layout.sidebar;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.OrganizationalUnitService;
import pl.bscisel.timetable.view.layout.sidebar.components.ClassGroupButton;
import pl.bscisel.timetable.view.layout.sidebar.components.OrgUnitButton;
import pl.bscisel.timetable.view.layout.sidebar.components.OrgUnitDiv;
import pl.bscisel.timetable.view.timetables.ClassGroupTimetableView;

import java.util.List;

// LLR_332
public class OrganizationalUnitsNav extends VerticalLayout {
    private final OrganizationalUnitService orgUnitService;
    private final ClassGroupService classGroupService;

    public OrganizationalUnitsNav(OrganizationalUnitService orgUnitService,
                                  ClassGroupService classGroupService) {
        this.orgUnitService = orgUnitService;
        this.classGroupService = classGroupService;

        setupTopLevelUnits();
        setSpacing(false);
    }

    private void setupTopLevelUnits() {
        List<OrganizationalUnit> organizationalUnits = orgUnitService.findTopLevelUnits();
        add(makeDivsForUnits(organizationalUnits));
    }

    private OrgUnitDiv[] makeDivsForUnits(List<OrganizationalUnit> organizationalUnits) {
        return organizationalUnits.stream().map(this::makeDivForUnit).toArray(OrgUnitDiv[]::new);
    }

    private OrgUnitDiv makeDivForUnit(OrganizationalUnit orgUnit) {
        OrgUnitButton parentUnitBtn = new OrgUnitButton(orgUnit.getName());
        OrgUnitDiv unitDiv = new OrgUnitDiv(parentUnitBtn);

        unitDiv.add(parentUnitBtn);
        Long orgUnitId = orgUnit.getId();
        parentUnitBtn.addClickListener(event -> {
            if (parentUnitBtn.isPressed()) {
                if (unitDiv.isChildrenSet()) {
                    unitDiv.showChildren();
                } else {
                    findAndSetChildren(orgUnitId, unitDiv);
                }
            } else {
                unitDiv.hideChildren();
            }
        });
        return unitDiv;
    }

    private void findAndSetChildren(Long orgUnitId, OrgUnitDiv unitDiv) {
        List<OrganizationalUnit> childUnits = orgUnitService.findChildrenByUnitId(orgUnitId);
        List<ClassGroup> classGroups = classGroupService.findByOrganizationalUnitId(orgUnitId);
        Div childrenDiv = new Div();
        if (!childUnits.isEmpty()) {
            childrenDiv.add(makeDivsForUnits(childUnits));
        }
        if (!classGroups.isEmpty()) {
            childrenDiv.add(makeButtonsForClassGroups(classGroups));
        }
        unitDiv.setChildren(childrenDiv);
    }

    private ClassGroupButton[] makeButtonsForClassGroups(List<ClassGroup> classGroups) {
        return classGroups.stream().map(this::makeButtonForClassGroup).toArray(ClassGroupButton[]::new);
    }

    private ClassGroupButton makeButtonForClassGroup(ClassGroup classGroup) {
        ClassGroupButton button = new ClassGroupButton(classGroup.getName());
        button.addClickListener(event -> getUI().ifPresent(x -> x.navigate(ClassGroupTimetableView.class, classGroup.getId())));
        return button;
    }
}
