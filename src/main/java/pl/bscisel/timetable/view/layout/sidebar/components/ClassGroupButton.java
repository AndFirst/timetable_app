package pl.bscisel.timetable.view.layout.sidebar.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ClassGroupButton extends Button {

    public ClassGroupButton(String text) {
        super(text);
        setIcon(VaadinIcon.GROUP.create());
        addClassName("nav-gototimetable-button");
        addClassName("nav-button");
        addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }
}
