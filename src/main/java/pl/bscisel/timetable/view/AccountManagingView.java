package pl.bscisel.timetable.view;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.form.AccountForm;
import pl.bscisel.timetable.service.AccountService;
import pl.bscisel.timetable.view.layout.MainLayout;

// LLR_320, LLR_322, LLR_323, LLR_325
@org.springframework.stereotype.Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Route(value = "accounts", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Timetable - Accounts")
public class AccountManagingView extends AbstractManagingView<Account, AccountForm, AccountService> {

    public AccountManagingView() {
    }

    @Override
    @Autowired
    void setForm(AccountForm form) {
        this.form = form;
    }

    @Override
    @Autowired
    void setService(AccountService accountService) {
        this.service = accountService;
    }

    @Override
    void configureFilter() {
        super.configureFilter();
        textFilter.setPlaceholder("Filter by email address...");
    }

    @Override
    void configureAddButton() {
        super.configureAddButton();
        addEntityButton.setText("Add account");
    }

    @Override
    void setGridColumns() {
        grid.setColumns("emailAddress");
        grid.addColumn(Account::formatRoles).setHeader("Roles");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }

    // LLR_324
    @Override
    void edit(Account account, boolean isNew) {
        super.edit(account, isNew);
        if (account == null)
            return;
        if (isNew)
            form.setMode(AccountForm.Mode.ADD);
        else
            form.setMode(AccountForm.Mode.EDIT);
    }

}
