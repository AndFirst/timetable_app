package pl.bscisel.timetable.views;

import com.vaadin.flow.component.applayout.AppLayout;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.sidebar.SideBar;
import pl.bscisel.timetable.views.topbar.TopBar;

public class MainLayout extends AppLayout {

    public MainLayout(SecurityService securityService,
                      OrganizationalUnitService orgUnitService,
                      ClassGroupService classGroupService) {
        setPrimarySection(Section.DRAWER);

        addToDrawer(new SideBar(orgUnitService, classGroupService));
        addToNavbar(new TopBar(securityService));
    }
}