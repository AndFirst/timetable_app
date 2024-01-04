package pl.bscisel.timetable.views.organizationalunits.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrganizationalUnitForm extends AbstractForm<OrganizationalUnit> {
    TextField name = new TextField("Organizational unit name");
    ComboBox<OrganizationalUnit> parentUnit = new ComboBox<>("Parent unit");
    TextArea description = new TextArea("Description");

    OrganizationalUnitService orgUnitService;

    public OrganizationalUnitForm(OrganizationalUnitService orgUnitService) {
        super(new BeanValidationBinder<>(OrganizationalUnit.class));
        this.orgUnitService = orgUnitService;

        configureFields();
        populateFields();
        setBindings();
        configureEnterShortcut(description);

        add(name, parentUnit, description, getButtons());
    }

    private void configureFields() {
        name.setRequired(true);
        parentUnit.setHelperText("If not selected, organizational unit will be top level. Organizational unit cannot be its own parent.");
    }

    private void populateFields() {
        parentUnit.setItemLabelGenerator(orgUnitService::getNameWithId);
        parentUnit.setItems(orgUnitService.findAll());
    }

    private void setBindings() {
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
    }

    Binder<OrganizationalUnit> getBinder() {
        return binder;
    }

}
