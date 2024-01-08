package pl.bscisel.timetable.form;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.service.ClassGroupService;
import pl.bscisel.timetable.service.OrganizationalUnitService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClassGroupForm extends AbstractForm<ClassGroup> {
    TextField name = new TextField("Class group name");
    ComboBox<OrganizationalUnit> organizationalUnit = new ComboBox<>("Organizational unit");
    TextArea description = new TextArea("Description");

    OrganizationalUnitService orgUnitService;
    ClassGroupService classGroupService;

    /**
     * Creates a new class group form.
     */
    public ClassGroupForm() {
        super(new BeanValidationBinder<>(ClassGroup.class));
    }

    /**
     * Sets the organizational unit service. Should be automatically autowired by Spring.
     * @param orgUnitService the organizational unit service
     */
    @Autowired
    public void setOrganizationalUnitService(OrganizationalUnitService orgUnitService) {
        this.orgUnitService = orgUnitService;
    }

    /**
     * Sets the class group service. Should be automatically autowired by Spring.
     * @param classGroupService the class group service
     */
    @Autowired
    public void setClassGroupService(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    @Override
    void configureForm() {

    }

    @Override
    void configureFields() {

    }

    @Override
    void setFieldsRequired() {
        name.setRequired(true);
        organizationalUnit.setRequired(true);
    }

    @Override
    void populateFields() {
        organizationalUnit.setItemLabelGenerator(orgUnitService::getNameWithId);
        organizationalUnit.setItems(orgUnitService.findAll());
    }

    @Override
    void setBindings() {
        binder.forField(name)
                .withValidator(new BeanValidator(ClassGroup.class, "name"))
                .withValidator(name -> {
                    if (binder.getBean() == null || binder.getBean().getOrganizationalUnit() == null)
                        return true;
                    return !classGroupService.existsByNameAndOrganizationalUnitId(name,
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

    @Override
    void configureEnterShortcut() {
        configureEnterShortcutWithFix(description);
    }

    @Override
    void addComponentsToForm() {
        add(name, organizationalUnit, description, createButtons());
    }

}
