package pl.bscisel.timetable.views.teachers;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.service.TeacherInfoService;
import pl.bscisel.timetable.data.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherInfoViewTest {

    TeacherInfoView view;
    TeacherInfoService service;
    UserService userService;
    TeacherInfoForm form;

    @BeforeEach
    public void setUp() {
        service = mock(TeacherInfoService.class);
        userService = mock(UserService.class);
        form = spy(TeacherInfoForm.class);
        form.setTeacherInfoService(service);
        form.setUserService(userService);
        view = spy(TeacherInfoView.class);
        view.setTeacherInfoService(service);
        view.setForm(form);
    }

    @Test
    public void testFormNotVisibleOnStart() {
        view.init();
        assertFalse(view.form.isVisible());
    }

    @Test
    public void testGridIsVisibleOnStart() {
        view.init();
        assertTrue(view.grid.isVisible());
    }

    @Test
    public void testToolbarIsVisibleOnStart() {
        view.init();
        assertTrue(view.textFilter.isVisible());
        assertTrue(view.addTeacherBtn.isVisible());
    }

    @Test
    public void testCloseForm() {
        view.closeForm();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testSaveTeacher() {
        TeacherInfo teacher = new TeacherInfo();
        view.saveTeacher(teacher);
        verify(service, times(1)).save(teacher);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testDeleteTeacher() {
        TeacherInfo teacher = new TeacherInfo();
        view.deleteTeacher(teacher);
        verify(service, times(1)).delete(teacher);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testConfigureForm() {
        view.configureForm();
        assertFalse(view.form.isVisible());
        verify(view.form, times(1)).addSaveAction(any());
        verify(view.form, times(1)).addDeleteAction(any());
        verify(view.form, times(1)).addCancelAction(any());
    }

    @Test
    public void testConfigureGrid() {
        List<TeacherInfo> teachers = createTeachers();

        view.configureGrid();

        assertEquals(5, view.grid.getColumns().size());
        for (int i = 0; i < 5; i++) {
            assertTrue(view.grid.getColumns().get(i).isAutoWidth());
        }

        view.grid.select(teachers.get(1));

        verify(view, times(1)).editTeacher(teachers.get(1));
    }

    @Test
    public void testConfigureToolbar() {
        view.configureToolbar();
        view.textFilter.setValue("test");
        verify(view, times(1)).updateItems();
        assertEquals(ValueChangeMode.LAZY , view.textFilter.getValueChangeMode());
        assertTrue(view.textFilter.isClearButtonVisible());
        assertEquals("Filter..." , view.textFilter.getPlaceholder());
    }

    @Test
    public void testCreateToolbar() {
        Component toolbar = view.createToolbar();
        assertEquals(2, toolbar.getChildren().count());
    }

    @Test
    public void testCreateContent() {
        Component content = view.createContent();
        assertEquals(2, content.getChildren().count());
    }

    @Test
    public void testCloseEditor() {
        view.configureForm();
        view.editTeacher(new TeacherInfo());
        assertTrue(view.form.isVisible());
        assertNotNull(view.form.getFormBean());
        view.closeEditor();
        assertFalse(view.form.isVisible());
        assertNull(view.form.getFormBean());
    }

    @Test
    public void testEditTeacher() {
        view.configureForm();
        assertFalse(view.form.isVisible());
        TeacherInfo teacher = new TeacherInfo();
        view.editTeacher(teacher);
        assertTrue(view.form.isVisible());
        assertEquals(teacher, view.form.getFormBean());
        view.editTeacher(null);
        assertFalse(view.form.isVisible());
        assertNull(view.form.getFormBean());
    }

    @Test
    public void testAddTeacher() {
        List<TeacherInfo> teachers = createTeachers();
        assertTrue(view.grid.getSelectedItems().isEmpty());
        view.grid.select(teachers.get(1));
        assertFalse(view.grid.getSelectedItems().isEmpty());

        view.addTeacher();
        assertTrue(view.grid.getSelectedItems().isEmpty());
        verify(view, times(1)).editTeacher(any());
    }

    @Test
    public void testUpdateItems() {
        view.textFilter.setValue("test");
        when(service.search("test")).thenReturn(createTeachers());
        view.updateItems();
        verify(service, times(1)).search("test");
        assertEquals(2, view.grid.getDataProvider().size(new Query<>()));
    }

    private List<TeacherInfo> createTeachers() {
        TeacherInfo teacher1 = new TeacherInfo();
        teacher1.setId(1L);
        teacher1.setName("Jan");
        teacher1.setSurname("Kowalski");

        TeacherInfo teacher2 = new TeacherInfo();
        teacher2.setId(2L);
        teacher2.setName("Adam");
        teacher2.setSurname("Nowak");
        return List.of(teacher1, teacher2);
    }
}