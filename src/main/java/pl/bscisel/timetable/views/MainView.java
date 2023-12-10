package pl.bscisel.timetable.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Timetable")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class MainView extends VerticalLayout {

    MainView() {
        add(new H1("Hello"));
    }

}
