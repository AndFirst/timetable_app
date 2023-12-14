package pl.bscisel.timetable.views.organizationalunits;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClassGroupForm extends FormLayout {
    TextField name = new TextField("Class group name");
    ComboBox<OrganizationalUnit> organizationalUnit = new ComboBox<>("Organizational unit");
    TextArea description = new TextArea("Description");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    ShortcutRegistration enterShortcut;
    Binder<ClassGroup> binder = new BeanValidationBinder<>(ClassGroup.class);
    OrganizationalUnitService orgUnitService;
    ClassGroupService classGroupService;

    public ClassGroupForm(OrganizationalUnitService orgUnitService,
                          ClassGroupService classGroupService) {
        this.orgUnitService = orgUnitService;
        this.classGroupService = classGroupService;

        configureFormFields();
        configureButtons();

        add(name, organizationalUnit, description, getButtons());
    }

    private void configureFormFields() {
        name.setRequired(true);
        organizationalUnit.setRequired(true);

        populateFields();
        setBindings();
    }

    private void populateFields() {
        organizationalUnit.setItemLabelGenerator(orgUnitService::getNameWithId);
        organizationalUnit.setItems(orgUnitService.findAll());
    }

    private void setBindings() {
        binder.forField(name)
                .withValidator(new BeanValidator(ClassGroup.class, "name"))
                .withValidator(name -> {
                    if (binder.getBean() == null || binder.getBean().getOrganizationalUnit() == null)
                        return true;
                    return !classGroupService.classGroupExistsByNameAndOrganizationalUnitId(name,
                            binder.getBean().getOrganizationalUnit().getId(),
                            binder.getBean().getId());
                }, "Class group with this name already exists in this organizational unit")
                .bind(ClassGroup::getName, ClassGroup::setName);
        binder.forField(organizationalUnit)
                .withValidator(new BeanValidator(ClassGroup.class, "organizationalUnit"))
                .bind(ClassGroup::getOrganizationalUnit, ClassGroup::setOrganizationalUnit);
        binder.forField(description)
                .withValidator(new BeanValidator(ClassGroup.class, "description"))
                .bind(ClassGroup::getDescription, ClassGroup::setDescription);
    }

    private void configureButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        close.addClickShortcut(Key.ESCAPE);
        configureEnterShortcut();
    }

    /**
     * Function that validates and saves the form if it is valid.
     */
    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private Component getButtons() {
        return new HorizontalLayout(save, delete, close);
    }

    public void setClassGroup(ClassGroup classGroup) {
        binder.setBean(classGroup);
    }

    /**
     * Configure the enter shortcut for the form.
     * <p>
     * The shortcut will be bound and unbound when the description field is
     * focused or blurred, respectively.
     * <p>
     * This is a workaround to fix the following issue:
     * When description field is focused, hitting enter was triggering the save instead of adding a new line.
     */
    private void configureEnterShortcut() {
        if (enterShortcut == null)
            enterShortcut = save.addClickShortcut(Key.ENTER);

        description.addBlurListener(e -> enterShortcut = save.addClickShortcut(Key.ENTER));

        description.addFocusListener(e -> enterShortcut.remove());
    }

    @Getter
    public static abstract class ClassGroupFormEvent extends ComponentEvent<ClassGroupForm> {
        private final ClassGroup classGroup;

        protected ClassGroupFormEvent(ClassGroupForm source, ClassGroup classGroup) {
            super(source, false);
            this.classGroup = classGroup;
        }

    }

    public static class SaveEvent extends ClassGroupFormEvent {
        SaveEvent(ClassGroupForm source, ClassGroup classGroup) {
            super(source, classGroup);
        }
    }

    public static class DeleteEvent extends ClassGroupFormEvent {
        DeleteEvent(ClassGroupForm source, ClassGroup classGroup) {
            super(source, classGroup);
        }
    }

    public static class CloseEvent extends ClassGroupFormEvent {
        CloseEvent(ClassGroupForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }

}
