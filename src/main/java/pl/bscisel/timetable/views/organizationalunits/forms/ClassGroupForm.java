package pl.bscisel.timetable.views.organizationalunits.forms;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.service.ClassGroupService;
import pl.bscisel.timetable.data.service.OrganizationalUnitService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClassGroupForm extends AbstractForm<ClassGroup> {
    TextField name = new TextField("Class group name");
    ComboBox<OrganizationalUnit> organizationalUnit = new ComboBox<>("Organizational unit");
    TextArea description = new TextArea("Description");

    OrganizationalUnitService orgUnitService;
    ClassGroupService classGroupService;

    public ClassGroupForm(OrganizationalUnitService orgUnitService,
                          ClassGroupService classGroupService) {
        super(new BeanValidationBinder<>(ClassGroup.class));
        this.orgUnitService = orgUnitService;
        this.classGroupService = classGroupService;

        setRequiredFields();
        populateFields();
        setBindings();
        configureEnterShortcut();

        add(name, organizationalUnit, description, getButtons());
    }

    private void setRequiredFields() {
        name.setRequired(true);
        organizationalUnit.setRequired(true);
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
}
