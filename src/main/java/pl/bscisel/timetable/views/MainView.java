package pl.bscisel.timetable.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.Route;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.views.components.unitsnav.OrganizationalUnitsNav;

@Route("")
public class MainView extends AppLayout {


    public MainView(OrganizationalUnitService organizationalUnitService) {
        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Timetable");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        TabSheet tabSheet = new TabSheet();
        Tab groupsTab = new Tab(VaadinIcon.GROUP.create(), new Span("Groups"));
        tabSheet.add(groupsTab, new OrganizationalUnitsNav(organizationalUnitService));
        tabSheet.add(new Tab(VaadinIcon.USER.create(), new Span("Teachers")), new H1("Teachers"));

        tabSheet.addClassName("nav-tab-sheet");
        addToDrawer(tabSheet);
        addToNavbar(toggle, title);
        setPrimarySection(Section.DRAWER);

    }
}