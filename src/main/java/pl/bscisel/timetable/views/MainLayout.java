package pl.bscisel.timetable.views;

import com.vaadin.flow.component.applayout.AppLayout;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.sidebar.SideBar;
import pl.bscisel.timetable.views.topbar.TopBar;

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