package pl.bscisel.timetable.views.components.unitsnav;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;

public class ClassGroupButton extends Button {

    public ClassGroupButton(String text) {
        super(text);
        setIcon(VaadinIcon.GROUP.create());
        addClassName("class-group-button");
        addClassName("org-unit-button");
        addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }
}
