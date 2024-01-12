package pl.bscisel.timetable.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Account;
import pl.bscisel.timetable.form.AccountForm;
import pl.bscisel.timetable.service.AccountService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountManagingViewTest {

    AccountManagingView view;
    AccountService service;
    AccountForm form;

    @BeforeEach
    void setUp() {
        service = mock(AccountService.class);
        when(service.getEntityClass()).thenReturn(Account.class);

        form = spy(AccountForm.class);
        form.setAccountService(service);

        view = spy(AccountManagingView.class);
        view.setService(service);
        view.setForm(form);
    }

    @Test
    void configureFilter() {
        view.configureFilter();
        assertEquals("Filter by email address...", view.textFilter.getPlaceholder());
    }

    @Test
    void configureAddButton() {
        view.configureAddButton();
        assertEquals("Add account", view.addEntityButton.getText());
    }

    @Test
    void setGridColumns() {
        view.configureGrid();
        view.setGridColumns();
        assertEquals(2, view.grid.getColumns().size());
    }

    @Test
    void edit() {
        view.edit(null, true);
        verify(form, times(0)).setMode(any());
        view.edit(null, false);
        verify(form, times(0)).setMode(any());
        Account account = new Account();
        view.edit(account, true);
        verify(form).setMode(AccountForm.Mode.ADD);
        view.edit(account, false);
        verify(form).setMode(AccountForm.Mode.EDIT);
    }
}