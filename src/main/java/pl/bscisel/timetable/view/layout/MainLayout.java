package pl.bscisel.timetable.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.bscisel.timetable.view.layout.sidebar.SideBar;
import pl.bscisel.timetable.view.layout.topbar.TopBar;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MainLayout extends AppLayout {

    public MainLayout(ApplicationContext applicationContext) {
        setPrimarySection(Section.DRAWER);

        addToDrawer(applicationContext.getBean(SideBar.class));
        addToNavbar(applicationContext.getBean(TopBar.class));
    }
}