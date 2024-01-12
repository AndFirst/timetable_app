package pl.bscisel.timetable.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.form.TeacherInfoForm;
import pl.bscisel.timetable.service.AccountService;
import pl.bscisel.timetable.service.TeacherInfoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TeacherInfoManagingViewTest {

    TeacherInfoManagingView view;
    TeacherInfoService service;
    AccountService accountService;
    TeacherInfoForm form;

    @BeforeEach
    public void setUp() {
        service = mock(TeacherInfoService.class);
        when(service.getEntityClass()).thenReturn(TeacherInfo.class);

        accountService = mock(AccountService.class);

        form = spy(TeacherInfoForm.class);
        form.setTeacherInfoService(service);
        form.setAccountService(accountService);

        view = spy(TeacherInfoManagingView.class);
        view.setService(service);
        view.setForm(form);
    }

    @Test
    public void configureFilter() {
        view.configureFilter();
        assertEquals("Filter...", view.textFilter.getPlaceholder());
    }

    @Test
    public void configureAddButton() {
        view.configureAddButton();
        assertEquals("Add teacher", view.addEntityButton.getText());
    }

    @Test
    public void setGridColumns() {
        view.configureGrid();
        view.setGridColumns();
        assertEquals(5, view.grid.getColumns().size());
    }

}