package pl.bscisel.timetable.views.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

public class SideBar extends Div {

    public SideBar(OrganizationalUnitService orgUnitService, boolean adminView) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addClassName("nav-tab-sheet");

        Tab groupsTab = new Tab(VaadinIcon.GROUP.create(), new Span("Groups"));
        Component groupsComponent = new OrganizationalUnitsNav(orgUnitService, adminView);

        Tab teachersTab = new Tab(VaadinIcon.USER.create(), new Span("Teachers"));
        Component teachersComponent = new Div((new Text("This is where teachers will one day be")));

        tabSheet.add(groupsTab, groupsComponent);
        tabSheet.add(teachersTab, teachersComponent);

        add(tabSheet);
    }
}
