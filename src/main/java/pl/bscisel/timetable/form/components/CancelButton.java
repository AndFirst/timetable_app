package pl.bscisel.timetable.form.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class CancelButton extends Button {
    public CancelButton() {
        super("Cancel");
        addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    }
}
