package pl.bscisel.timetable.views.sidebar;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.views.TimetableView;
import pl.bscisel.timetable.views.sidebar.components.ClassGroupButton;
import pl.bscisel.timetable.views.sidebar.components.OrgUnitButton;
import pl.bscisel.timetable.views.sidebar.components.OrgUnitDiv;

import java.util.List;


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
        parentUnitBtn.addClickListener(event -> {
            if (parentUnitBtn.isPressed()) {
                if (unitDiv.isChildrenSet()) {
                    unitDiv.showChildren();
                } else {
                    findAndSetChildren(orgUnit, unitDiv);
                }
            } else {
                unitDiv.hideChildren();
            }
        });
        return unitDiv;
    }

    private void findAndSetChildren(OrganizationalUnit orgUnit, OrgUnitDiv unitDiv) {
        List<OrganizationalUnit> childUnits = orgUnitService.findChildrenByUnitId(orgUnit.getId());
        List<ClassGroup> classGroups;
        if (childUnits != null && !childUnits.isEmpty()) {
            Div childrenDiv = new Div(makeDivsForUnits(childUnits));
            unitDiv.setChildren(childrenDiv);
        } else if ((classGroups = classGroupService.findClassGroupsByOrganizationalUnitId(orgUnit.getId())) != null && !classGroups.isEmpty()) {
            Div childrenDiv = new Div(makeButtonsForClassGroups(classGroups));
            unitDiv.setChildren(childrenDiv);
        } else {
            unitDiv.setChildren(new Div());
        }
    }

    private ClassGroupButton[] makeButtonsForClassGroups(List<ClassGroup> classGroups) {
        return classGroups.stream().map(this::makeButtonForClassGroup).toArray(ClassGroupButton[]::new);
    }

    private ClassGroupButton makeButtonForClassGroup(ClassGroup classGroup) {
        ClassGroupButton button = new ClassGroupButton(classGroup.getName());
        button.addClickListener(event -> getUI().ifPresent(x -> x.navigate(TimetableView.class)));
        return button;
    }
}
