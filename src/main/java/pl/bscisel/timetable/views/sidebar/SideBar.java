package pl.bscisel.timetable.views.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.data.service.TeacherInfoService;

public class SideBar extends Div {

    public SideBar(OrganizationalUnitService orgUnitService,
                   ClassGroupService classGroupService,
                   TeacherInfoService teacherInfoService) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addClassName("nav-tab-sheet");

        Tab groupsTab = new Tab(VaadinIcon.GROUP.create(), new Span("Groups"));
        Component groupsComponent = new OrganizationalUnitsNav(orgUnitService, classGroupService);

        Tab teachersTab = new Tab(VaadinIcon.USER.create(), new Span("Teachers"));
        Component teachersComponent = new TeachersNav(teacherInfoService);

        tabSheet.add(groupsTab, groupsComponent);
        tabSheet.add(teachersTab, teachersComponent);

        add(tabSheet);
    }
}
