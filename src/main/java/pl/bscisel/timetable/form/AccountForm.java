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
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.Role;
import pl.bscisel.timetable.service.AccountService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountForm extends AbstractForm<Account> {
    Mode mode = Mode.ADD;

    TextField emailAddress = new TextField("Email address");
    PasswordField password = new PasswordField();
    MultiSelectComboBox<Role> roles = new MultiSelectComboBox<>("Roles");

    AccountService accountService;

    /**
     * Creates a new account form.
     */
    public AccountForm() {
        super(new BeanValidationBinder<>(Account.class));
    }

    /**
     * Sets the account service. Should be automatically autowired by Spring.
     * @param accountService the account service
     */
    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Overrides the init method to set default mode to ADD.
     */
    @Override
    @PostConstruct
    void init() {
        super.init();
        setMode(Mode.ADD);
    }

    /**
     * Sets the mode of the form. It sets fields to be required or not and changes the password field label depending on the mode.
     * @param mode the mode to set
     */
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
        roles.setItems(accountService.findAllRoles());
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
                .withValidator(new BeanValidator(Account.class, "emailAddress"))
                .withValidator(email -> {
                    if (binder.getBean() == null)
                        return true;
                    return !accountService.existsByEmailAddress(email, binder.getBean().getId());
                }, "Account with this email address already exists")
                .bind(Account::getEmailAddress, Account::setEmailAddress);
        binder.forField(password)
                .withValidator(passwordValidator)
                .bind(ignored -> "", Account::setPassword);
        binder.forField(roles)
                .withValidator(new BeanValidator(Account.class, "roles"))
                .bind(Account::getRoles, Account::setRoles);
    }

    @Override
    void configureEnterShortcut() {
        configureEnterShortcutWithFix();
    }

    @Override
    void addComponentsToForm() {
        add(emailAddress, password, roles, createButtons());
    }

    public enum Mode {
        ADD, EDIT
    }

}
