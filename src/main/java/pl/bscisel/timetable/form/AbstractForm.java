package pl.bscisel.timetable.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.ShortcutRegistration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import jakarta.annotation.PostConstruct;
import pl.bscisel.timetable.form.components.CancelButton;
import pl.bscisel.timetable.form.components.DeleteButton;
import pl.bscisel.timetable.form.components.SaveButton;
import pl.bscisel.timetable.form.interfaces.FormEventAction;

public abstract class AbstractForm<T> extends FormLayout {
    protected final Binder<T> binder;
    private final Button save = new SaveButton();
    private final Button delete = new DeleteButton();
    private final Button cancel = new CancelButton();
    private ShortcutRegistration enterShortcut;

    public AbstractForm(Binder<T> binder) {
        this.binder = binder;
    }

    @PostConstruct
    void init() {
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        configureForm();
        configureFields();
        setFieldsRequired();
        populateFields();
        setBindings();
        addComponentsToForm();
        configureShortcuts();
        configureEnterShortcut();
    }

    abstract void configureForm();

    abstract void configureFields();

    abstract void setFieldsRequired();

    abstract void populateFields();

    abstract void setBindings();

    abstract void configureEnterShortcut();

    abstract void addComponentsToForm();


    void configureShortcuts() {
        cancel.addClickShortcut(Key.ESCAPE);
    }

    /**
     * Configure the enter shortcut for the form.
     * <p>
     * The shortcut will be bound and unbound when the text area fields are
     * focused or blurred, respectively.
     * <p>
     * This is a workaround to fix the following issue:
     * When text area field is focused, hitting enter was triggering the save instead of adding a new line.
     */
    public void configureEnterShortcutWithFix(TextArea... textAreas) {
        if (enterShortcut == null)
            enterShortcut = save.addClickShortcut(Key.ENTER);

        for (TextArea textArea : textAreas) {
            textArea.addBlurListener(e -> enterShortcut = save.addClickShortcut(Key.ENTER));
            textArea.addFocusListener(e -> enterShortcut.remove());
        }
    }

    public Component createButtons() {
        return new HorizontalLayout(save, delete, cancel);
    }

    public void addDeleteAction(FormEventAction<T> action) {
        delete.addClickListener(e -> action.run(binder.getBean()));
    }

    public void addSaveAction(FormEventAction<T> action) {
        save.addClickListener(e -> {
            if (binder.isValid()) {
                action.run(binder.getBean());
            }
        });
    }

    public void addCancelAction(FormEventAction<T> action) {
        cancel.addClickListener(e -> action.run(binder.getBean()));
    }

    public T getFormBean() {
        return binder.getBean();
    }

    public void setFormBean(T bean) {
        binder.setBean(bean);
    }
}
