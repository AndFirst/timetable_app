package pl.bscisel.timetable.form;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.service.AccountService;
import pl.bscisel.timetable.service.TeacherInfoService;

@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeacherInfoForm extends AbstractForm<TeacherInfo> {
    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField degree = new TextField("Degree");
    TextField phoneNumber = new TextField("Phone number");
    TextArea biography = new TextArea("Biography");
    ComboBox<Account> account = new ComboBox<>("Account");

    AccountService accountService;
    TeacherInfoService teacherInfoService;

    /**
     * Creates a new teacher info form.
     */
    public TeacherInfoForm() {
        super(new BeanValidationBinder<>(TeacherInfo.class));
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
     * Sets the teacher info service. Should be automatically autowired by Spring.
     * @param teacherInfoService the teacher info service
     */
    @Autowired
    public void setTeacherInfoService(TeacherInfoService teacherInfoService) {
        this.teacherInfoService = teacherInfoService;
    }

    @Override
    void configureForm() {
        setResponsiveSteps(new ResponsiveStep("0", 1));
        setMaxWidth("33.33%");
    }

    @Override
    void configureFields() {

    }

    @Override
    void setFieldsRequired() {
        name.setRequired(true);
        surname.setRequired(true);
    }

    @Override
    void populateFields() {
        account.setItems(accountService.findAllAccounts());
        account.setItemLabelGenerator(account -> "#" + account.getId() + " " + account.getEmailAddress());
    }

    @Override
    void setBindings() {
        binder.forField(name)
                .withValidator(new BeanValidator(TeacherInfo.class, "name"))
                .bind(TeacherInfo::getName, TeacherInfo::setName);

        binder.forField(surname)
                .withValidator(new BeanValidator(TeacherInfo.class, "surname"))
                .bind(TeacherInfo::getSurname, TeacherInfo::setSurname);

        binder.forField(degree)
                .withValidator(new BeanValidator(TeacherInfo.class, "degree"))
                .bind(TeacherInfo::getDegree, TeacherInfo::setDegree);

        binder.forField(phoneNumber)
                .withValidator(new BeanValidator(TeacherInfo.class, "phoneNumber"))
                .bind(TeacherInfo::getPhoneNumber, TeacherInfo::setPhoneNumber);

        binder.forField(biography)
                .withValidator(new BeanValidator(TeacherInfo.class, "biography"))
                .bind(TeacherInfo::getBiography, TeacherInfo::setBiography);

        binder.forField(account)
                .withValidator(new BeanValidator(TeacherInfo.class, "account"))
                .withValidator(account -> {
                    if (binder.getBean() == null || binder.getBean().getAccount() == null)
                        return true;
                    return !teacherInfoService.existsByAccountId(account.getId(), binder.getBean().getId());
                }, "Selected account is already assigned to another teacher")
                .bind(TeacherInfo::getAccount, TeacherInfo::setAccount);
    }

    @Override
    void configureEnterShortcut() {
        configureEnterShortcutWithFix(biography);
    }

    @Override
    void addComponentsToForm() {
        add(name, surname, degree, phoneNumber, biography, account, createButtons());
    }

}
