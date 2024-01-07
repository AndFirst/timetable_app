package pl.bscisel.timetable.form;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.BeanValidator;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.service.UserService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserForm extends AbstractForm<User> {
    Mode mode = Mode.ADD;

    TextField emailAddress = new TextField("Email address");
    PasswordField password = new PasswordField();
    MultiSelectComboBox<Role> roles = new MultiSelectComboBox<>("Roles");

    UserService userService;

    public UserForm() {
        super(new BeanValidationBinder<>(User.class));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostConstruct
    void init() {
        super.init();
        setMode(Mode.ADD);
    }

    public void setMode(Mode mode) {
        if (mode == Mode.ADD) {
            password.setLabel("Password");
            password.setRequired(true);
        } else if (mode == Mode.EDIT) {
            password.setLabel("Set new password");
            password.setRequired(false);
        }
        this.mode = mode;
    }

    @Override
    void configureForm() {

    }

    @Override
    void configureFields() {
        emailAddress.setAutocomplete(Autocomplete.OFF);
        password.setAutocomplete(Autocomplete.OFF);
    }

    @Override
    void setFieldsRequired() {
        emailAddress.setRequired(true);
    }

    @Override
    void populateFields() {
        roles.setItems(userService.findAllRoles());
        roles.setItemLabelGenerator(Role::getName);
    }

    @Override
    void setBindings() {
        Validator<String> passwordValidator = (value, context) -> {
            if (mode == Mode.ADD && (value == null || value.isEmpty()))
                return ValidationResult.error("You need to provide a password");
            if (mode == Mode.EDIT && (value == null || value.isEmpty()))
                return ValidationResult.ok();
            if (value.length() < 8)
                return ValidationResult.error("Password must be at least 8 characters long");
            if (value.length() > 128)
                return ValidationResult.error("Password cannot be longer than 128 characters");
            return ValidationResult.ok();
        };

        binder.forField(emailAddress)
                .withValidator(new BeanValidator(User.class, "emailAddress"))
                .withValidator(email -> {
                    if (binder.getBean() == null)
                        return true;
                    return !userService.userExistsByEmailAddress(email, binder.getBean().getId());
                }, "User with this email address already exists")
                .bind(User::getEmailAddress, User::setEmailAddress);
        binder.forField(password)
                .withValidator(passwordValidator)
                .bind(ignored -> "", User::setPassword);
        binder.forField(roles)
                .withValidator(new BeanValidator(User.class, "roles"))
                .bind(User::getRoles, User::setRoles);
    }

    @Override
    void configureEnterShortcut() {
        configureEnterShortcutWithFix();
    }

    @Override
    void addComponentsToForm() {
        add(emailAddress, password, roles, createButtons());
    }

    Binder<User> getBinder() {
        return binder;
    }

    public enum Mode {
        ADD, EDIT
    }

}
