package pl.bscisel.timetable.views.sidebar.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class TeacherButton extends Button {

    public TeacherButton(String text) {
        super(text);
        setIcon(VaadinIcon.USER.create());
        addClassName("class-group-button");
        addClassName("org-unit-button");
        addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }
}
