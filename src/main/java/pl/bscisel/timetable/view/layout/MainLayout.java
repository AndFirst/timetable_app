package pl.bscisel.timetable.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.OrganizationalUnitService;
import pl.bscisel.timetable.service.TeacherInfoService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.view.layout.sidebar.SideBar;
import pl.bscisel.timetable.view.layout.topbar.TopBar;

public class MainLayout extends AppLayout {

    public MainLayout(SecurityService securityService,
                      OrganizationalUnitService orgUnitService,
                      ClassGroupService classGroupService,
                      TeacherInfoService teacherInfoService) {
        setPrimarySection(Section.DRAWER);

        addToDrawer(new SideBar(orgUnitService, classGroupService, teacherInfoService));
        addToNavbar(new TopBar(securityService));
    }
}