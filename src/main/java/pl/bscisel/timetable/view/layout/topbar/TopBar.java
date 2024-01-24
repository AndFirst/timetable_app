package pl.bscisel.timetable.view.layout.topbar;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.bscisel.timetable.security.SecurityService;
import pl.bscisel.timetable.view.AccountManagingView;
import pl.bscisel.timetable.view.CourseManagingView;
import pl.bscisel.timetable.view.MainView;
import pl.bscisel.timetable.view.TeacherInfoManagingView;
import pl.bscisel.timetable.view.login.LoginView;
import pl.bscisel.timetable.view.organizationalunits.OrganizationalUnitView;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TopBar extends HorizontalLayout {

    SecurityService securityService;

    public TopBar(SecurityService securityService) {
        this.securityService = securityService;

        setStyling();

        add(getLeftPart());
        add(getCenterPart());
        add(getRightPart());
    }

    private HorizontalLayout getLeftPart() {
        HorizontalLayout layout = new HorizontalLayout(new DrawerToggle(), getSiteLogo());
        layout.setSpacing(false);

        layout.getStyle().set("margin", "0 var(--lumo-space-m) 0 3px");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        return layout;
    }

    // LLR_300
    private HorizontalLayout getCenterPart() {
        HorizontalLayout layout = new HorizontalLayout();

        if (securityService.isUserAdmin()) {
            Map<Tab, Runnable> tabsActions = new LinkedHashMap<>() {{
                put(new Tab("Home"), () -> getUI().ifPresent(ui -> ui.navigate(MainView.class)));
                put(new Tab("Organizational units"), () -> getUI().ifPresent(ui -> ui.navigate(OrganizationalUnitView.class)));
                put(new Tab("Courses"), () -> getUI().ifPresent(ui -> ui.navigate(CourseManagingView.class)));
                put(new Tab("Accounts"), () -> getUI().ifPresent(ui -> ui.navigate(AccountManagingView.class)));
                put(new Tab("Teachers"), () -> getUI().ifPresent(ui -> ui.navigate(TeacherInfoManagingView.class)));
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

    private HorizontalLayout getRightPart() {
        HorizontalLayout layout = new HorizontalLayout();

        layout.getStyle().set("margin-right", "var(--lumo-space-m)");
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        if (securityService.isUserLoggedIn()) {
            H5 loggedAsText = new H5("Logged in as " + securityService.getAuthenticatedTimetableAccount().getEmailAddress());
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
