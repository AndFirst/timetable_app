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

    /**
     * Function which initializes the form.
     */
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

    /**
     * Function which configures the form. It should be used to apply form specific settings.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void configureForm();

    /**
     * Function which configures the fields. It should be used to apply field specific settings.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void configureFields();

    /**
     * Function which sets the fields as required.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void setFieldsRequired();

    /**
     * Function which populates the fields with data.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void populateFields();

    /**
     * Function which binds the fields to the bean.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void setBindings();

    /**
     * Function which configures the enter shortcut for the form. It should call the configureEnterShortcutWithFix() with all the text area fields.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void configureEnterShortcut();


    /**
     * Function which adds the components to the form.
     * <p>
     * It is called in the init() method.
     */
    protected abstract void addComponentsToForm();


    /**
     * Function which configures the shortcuts for the form.
     */
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

    /**
     * Function which creates layout for the buttons.
     *
     * @return the layout with buttons
     */
    public Component createButtons() {
        return new HorizontalLayout(save, delete, cancel);
    }

    /**
     * Function which adds the action to the delete button.
     *
     * @param action the action to be added
     */
    public void addDeleteAction(FormEventAction<T> action) {
        delete.addClickListener(e -> action.run(binder.getBean()));
    }

    /**
     * Function which adds the action to the save button.
     *
     * @param action the action to be added
     */
    public void addSaveAction(FormEventAction<T> action) {
        save.addClickListener(e -> {
            if (binder.isValid()) {
                action.run(binder.getBean());
            }
        });
    }

    /**
     * Function which adds the action to the cancel button.
     *
     * @param action the action to be added
     */
    public void addCancelAction(FormEventAction<T> action) {
        cancel.addClickListener(e -> action.run(binder.getBean()));
    }

    /**
     * Function which returns the form bean.
     *
     * @return the form bean
     */
    public T getFormBean() {
        return binder.getBean();
    }

    /**
     * Function which sets the form bean.
     *
     * @param bean the bean to be set
     */
    public void setFormBean(T bean) {
        binder.setBean(bean);
    }

    Binder<T> getBinder() {
        return binder;
    }
}
