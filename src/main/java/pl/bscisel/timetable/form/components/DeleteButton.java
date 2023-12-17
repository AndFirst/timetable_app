package pl.bscisel.timetable.form.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;

public class DeleteButton extends Button {
    public DeleteButton() {
        super("Delete");
        addThemeVariants(ButtonVariant.LUMO_ERROR);
    }
}
