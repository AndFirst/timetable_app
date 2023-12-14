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
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizationalUnitForm extends FormLayout {
    TextField name = new TextField("Organizational unit name");
    ComboBox<OrganizationalUnit> parentUnit = new ComboBox<>("Parent unit");
    TextArea description = new TextArea("Description");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    ShortcutRegistration enterShortcut;

    Binder<OrganizationalUnit> binder = new BeanValidationBinder<>(OrganizationalUnit.class);

    OrganizationalUnitService orgUnitService;

    public OrganizationalUnitForm(OrganizationalUnitService orgUnitService) {
        this.orgUnitService = orgUnitService;
        parentUnit.setItemLabelGenerator(orgUnitService::getNameWithId);
        parentUnit.setItems(orgUnitService.findAll());

        name.setRequired(true);
        parentUnit.setHelperText("If not selected, organizational unit will be top level. Organizational unit cannot be its own parent.");
        binder.forField(name)
                .withValidator(new BeanValidator(OrganizationalUnit.class, "name"))
                .withValidator(name -> {
                    if (binder.getBean() == null)
                        return true;
                    return !orgUnitService.organizationalUnitExistsByNameAndParentUnitId(
                            name,
                            binder.getBean().getParentUnit() != null ? binder.getBean().getParentUnit().getId() : null,
                            binder.getBean().getId());
                }, "Organizational unit with this name already exists on this level")
                .bind(OrganizationalUnit::getName, OrganizationalUnit::setName);
        binder.forField(description)
                .withValidator(new BeanValidator(OrganizationalUnit.class, "description"))
                .bind(OrganizationalUnit::getDescription, OrganizationalUnit::setDescription);
        binder.forField(parentUnit)
                .withValidator(new BeanValidator(OrganizationalUnit.class, "parentUnit"))
                .withValidator(parentUnit -> {
                    if (binder.getBean() == null) return true;
                    if (parentUnit == null) return true;
                    if (parentUnit.getId() == null) return true;
                    return !parentUnit.getId().equals(binder.getBean().getId());
                }, "Organizational unit cannot be its own parent")
                .bind(OrganizationalUnit::getParentUnit, OrganizationalUnit::setParentUnit);

        configureButtons();

        add(name, parentUnit, description, getButtons());
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

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    private Component getButtons() {
        return new HorizontalLayout(save, delete, close);
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        binder.setBean(organizationalUnit);
    }

    private void configureEnterShortcut() {
        if (enterShortcut == null)
            enterShortcut = save.addClickShortcut(Key.ENTER);

        description.addBlurListener(e -> {
            enterShortcut = save.addClickShortcut(Key.ENTER);
        });

        description.addFocusListener(e -> {
            enterShortcut.remove();
        });
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

    @Getter
    public static abstract class OrganizationalUnitFormEvent extends ComponentEvent<OrganizationalUnitForm> {
        private final OrganizationalUnit organizationalUnit;

        protected OrganizationalUnitFormEvent(OrganizationalUnitForm source, OrganizationalUnit organizationalUnit) {
            super(source, false);
            this.organizationalUnit = organizationalUnit;
        }

    }

    public static class SaveEvent extends OrganizationalUnitFormEvent {
        SaveEvent(OrganizationalUnitForm source, OrganizationalUnit organizationalUnit) {
            super(source, organizationalUnit);
        }
    }

    public static class DeleteEvent extends OrganizationalUnitFormEvent {
        DeleteEvent(OrganizationalUnitForm source, OrganizationalUnit organizationalUnit) {
            super(source, organizationalUnit);
        }
    }

    public static class CloseEvent extends OrganizationalUnitFormEvent {
        CloseEvent(OrganizationalUnitForm source) {
            super(source, null);
        }
    }

}
