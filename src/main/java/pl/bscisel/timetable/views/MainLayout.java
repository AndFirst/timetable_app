package pl.bscisel.timetable.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.sidebar.OrganizationalUnitsNav;

public class MainLayout extends AppLayout {

    private SecurityService securityService;

    public MainLayout(@Autowired SecurityService securityService, @Autowired OrganizationalUnitService organizationalUnitService) {
        this.securityService = securityService;
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

        if (this.securityService.getAuthenticatedSpringUser() != null) {
            Button logout = new Button("Logout", click -> securityService.logout());
            addToNavbar(logout);
        }
    }
}