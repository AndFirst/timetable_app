package pl.bscisel.timetable.views.topbar;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.views.course.CourseView;
import pl.bscisel.timetable.views.LoginView;
import pl.bscisel.timetable.views.MainView;
import pl.bscisel.timetable.views.organizationalunits.OrganizationalUnitView;

import java.util.LinkedHashMap;
import java.util.Map;

public class TopBar extends HorizontalLayout {
    public TopBar(SecurityService securityService) {
        setStyling();

        add(getLeftPart());
        add(getCenterPart(securityService));
        add(getRightPart(securityService));
    }

    private HorizontalLayout getLeftPart() {
        HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(), getSiteLogo());
        layout.setSpacing(false);

        layout.getStyle().set("margin", "0 var(--lumo-space-m) 0 3px");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        return layout;
    }


    private HorizontalLayout getCenterPart(SecurityService securityService) {
        HorizontalLayout layout = new HorizontalLayout();

        if (securityService.isUserAdmin()) {
            Map<Tab, Runnable> tabsActions = new LinkedHashMap<>() {{
                put(new Tab("Home"), () -> getUI().ifPresent(ui -> ui.navigate(MainView.class)));
                put(new Tab("Organizational units"), () -> getUI().ifPresent(ui -> ui.navigate(OrganizationalUnitView.class)));
                put(new Tab("Courses"), () -> getUI().ifPresent(ui -> ui.navigate(CourseView.class)));
                put(new Tab("Users"), () -> Notification.show("Users"));
                put(new Tab("Consultations"), () -> Notification.show("Consultations"));
            }};

            Tabs tabs = new Tabs(tabsActions.keySet().toArray(Tab[]::new));
            tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
            tabs.getStyle().setMargin("auto");
            tabs.addSelectedChangeListener(event -> tabsActions.get(event.getSelectedTab()).run());
            layout.add(tabs);
        }

        layout.getStyle().set("margin-right", "var(--lumo-space-m)");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setWidthFull();
        return layout;
    }

    private HorizontalLayout getRightPart(SecurityService securityService) {
        HorizontalLayout layout = new HorizontalLayout();

        layout.getStyle().set("margin-right", "var(--lumo-space-m)");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        if (securityService.isUserLoggedIn()) {
            H5 loggedAsText = new H5("Logged in as " + securityService.getAuthenticatedTimetableUser().getUsername());
            loggedAsText.setWhiteSpace(HasText.WhiteSpace.NOWRAP);
            Button logoutBtn = new Button("Logout", click -> securityService.logout());
            layout.add(loggedAsText, logoutBtn);
        } else {
            Button loginBtn = new Button("Login", click -> getUI().ifPresent(x -> x.navigate(LoginView.class)));
            loginBtn.getStyle().set("color", "var(--lumo-contrast-50pct)");
            loginBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            loginBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
            layout.add(loginBtn);
        }
        return layout;
    }

    private void setStyling() {
        setWidthFull();
        setSpacing(false);
    }

    private H1 getSiteLogo() {
        H1 title = new H1("Timetable");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");
        return title;
    }
}
