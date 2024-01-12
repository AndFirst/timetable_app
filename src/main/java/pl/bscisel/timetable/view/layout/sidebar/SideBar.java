package pl.bscisel.timetable.view.layout.sidebar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SideBar extends Div {

    public SideBar(ApplicationContext applicationContext) {
        TabSheet tabSheet = new TabSheet();
        tabSheet.addClassName("nav-tab-sheet");

        Tab groupsTab = new Tab(VaadinIcon.GROUP.create(), new Span("Groups"));
        Component groupsComponent = applicationContext.getBean(OrganizationalUnitsNav.class);

        Tab teachersTab = new Tab(VaadinIcon.USER.create(), new Span("Teachers"));
        Component teachersComponent = applicationContext.getBean(TeachersNav.class);

        tabSheet.add(groupsTab, groupsComponent);
        tabSheet.add(teachersTab, teachersComponent);

        add(tabSheet);
    }
}
