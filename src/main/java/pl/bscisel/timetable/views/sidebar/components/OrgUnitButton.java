package pl.bscisel.timetable.views.sidebar.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Getter;

public class OrgUnitButton extends Button {

    @Getter
    private boolean pressed = false;

    private final Icon down = VaadinIcon.ANGLE_DOWN.create();
    private final Icon right = VaadinIcon.ANGLE_RIGHT.create();

    public OrgUnitButton(String text) {
        super(text);
        addClassName("org-unit-button");
        addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        setIcon(VaadinIcon.ANGLE_DOWN.create());
        addClickListener(event -> switchPressed());
    }

    private void switchPressed() {
        pressed = !pressed;
        if (pressed) {
            setIcon(right);
        } else {
            setIcon(down);
        }
    }

}
