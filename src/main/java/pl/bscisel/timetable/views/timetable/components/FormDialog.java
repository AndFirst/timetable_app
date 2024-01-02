package pl.bscisel.timetable.views.timetable.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dialog.Dialog;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

public class FormDialog extends Dialog {
    public FormDialog(AbstractForm<?> form, Component footer, String title) {
        add(form);
        getFooter().add(footer);
        configure(title);
    }

    private void configure(String title) {
        setHeaderTitle(title);
        setResizable(true);
        setDraggable(true);
        setCloseOnEsc(false); // form has it
        setCloseOnOutsideClick(true);
        setWidth("500px");
    }
}
