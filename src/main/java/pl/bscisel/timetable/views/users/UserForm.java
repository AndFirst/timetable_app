package pl.bscisel.timetable.views.users;

import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.textfield.Autocomplete;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.data.entity.User;
import pl.bscisel.timetable.data.service.UserService;
import pl.bscisel.timetable.form.interfaces.AbstractForm;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserForm extends AbstractForm<User> {
    Mode mode = Mode.ADD;

    TextField emailAddress = new TextField("Email address");
    PasswordField password = new PasswordField();
    MultiSelectComboBox<Role> roles = new MultiSelectComboBox<>("Roles");

    UserService userService;

    public UserForm(UserService userService) {
        super(new BeanValidationBinder<>(User.class));
        this.userService = userService;

        setMode(Mode.ADD);
        setRequiredFields();
        populateFields();
        setBindings();

        add(emailAddress, password, roles, getButtons());
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

    private void populateFields() {
        roles.setItems(userService.findAllRoles());
        roles.setItemLabelGenerator(Role::getName);
    }

    private void setRequiredFields() {
        emailAddress.setAutocomplete(Autocomplete.OFF);
        password.setAutocomplete(Autocomplete.OFF);

        emailAddress.setRequired(true);
    }

    private void setBindings() {
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
                .bind("emailAddress");
        binder.forField(password)
                .withValidator(passwordValidator)
                .bind(ignored -> "", User::setPassword);
        binder.forField(roles)
                .withValidator(new BeanValidator(User.class, "roles"))
                .bind("roles");

    }

    public enum Mode {
        ADD, EDIT
    }

}
