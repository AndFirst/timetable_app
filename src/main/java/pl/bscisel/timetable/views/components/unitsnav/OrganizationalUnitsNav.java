package pl.bscisel.timetable.views.components.unitsnav;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoIcon;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

import java.util.List;


public class OrganizationalUnitsNav extends VerticalLayout {
    OrganizationalUnitService orgUnitService;

    public OrganizationalUnitsNav(OrganizationalUnitService organizationalUnitService) {
        this.orgUnitService = organizationalUnitService;

        setupTopLevelUnits();
        setSpacing(false);
    }

    private void setupTopLevelUnits() {
        List<OrganizationalUnit> organizationalUnits = orgUnitService.getTopLevelUnits();
        add(makeDivsForUnits(organizationalUnits));
    }

    private OrgUnitDiv[] makeDivsForUnits(List<OrganizationalUnit> organizationalUnits) {
        return organizationalUnits.stream().map(this::makeDivForUnit).toArray(OrgUnitDiv[]::new);
    }

    private OrgUnitDiv makeDivForUnit(OrganizationalUnit orgUnit) {
        OrgUnitButton parentUnitBtn = new OrgUnitButton(orgUnit.getName(), orgUnitService.hasChildUnitsByOrganizationalUnitId(orgUnit.getId()) || orgUnitService.hasClassGroupsByOrganizationalUnitId(orgUnit.getId()));
        OrgUnitDiv unitDiv = new OrgUnitDiv(parentUnitBtn);

        var layout = new FlexLayout(parentUnitBtn);

        if (false) { // todo admin
            Button button = new Button(LumoIcon.EDIT.create());
            button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            parentUnitBtn.getStyle().set("flex", "1");
            button.getStyle().set("flex-shrink", "0");
            button.setHeightFull();
            layout.add(button);
        }

        unitDiv.add(layout);
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
        List<OrganizationalUnit> childUnits = orgUnitService.findOrganizationalUnitsByParentUnitId(orgUnit.getId());
        List<ClassGroup> classGroups;
        if (childUnits != null && !childUnits.isEmpty()) {
            Div childrenDiv = new Div(makeDivsForUnits(childUnits));
            unitDiv.setChildren(childrenDiv);
        } else if ((classGroups = orgUnitService.findClassGroupsByOrganizationalUnitId(orgUnit.getId())) != null && !classGroups.isEmpty()) {
            Div childrenDiv = new Div(makeButtonsForClassGroups(classGroups));
            unitDiv.setChildren(childrenDiv);
        }
    }

    private ClassGroupButton[] makeButtonsForClassGroups(List<ClassGroup> classGroups) {
        return classGroups.stream().map(this::makeButtonForClassGroup).toArray(ClassGroupButton[]::new);
    }

    private ClassGroupButton makeButtonForClassGroup(ClassGroup classGroup) {
        return new ClassGroupButton(classGroup.getName());
    }
}
