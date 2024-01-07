package pl.bscisel.timetable.view;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import pl.bscisel.timetable.view.layout.MainLayout;

@PageTitle("Timetable")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class MainView extends VerticalLayout {

    MainView() {
        add(new H3("Select the timetable to view from the navigation on the left side of the page."));
    }

}
