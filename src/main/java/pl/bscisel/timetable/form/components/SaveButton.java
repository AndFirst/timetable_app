package pl.bscisel.timetable.form.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class SaveButton extends Button {
    public SaveButton() {
        super("Save");
        addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }
}
