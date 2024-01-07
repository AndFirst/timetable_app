package pl.bscisel.timetable.views.course;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CourseViewTest {

    private final String emptyFilter = "";
    private final String filter = "TEST2";

    CourseService courseService;
    CourseForm courseForm;
    CourseView view;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        courseForm = spy(CourseForm.class);
        courseForm.setCourseService(courseService);

        view = spy(CourseView.class);
        view.setCourseService(courseService);
        view.setForm(courseForm);
    }

    private void mockCourseSearch() {
        Course course = new Course();
        course.setName("Test course");
        course.setCode("TEST");

        Course course1 = new Course();
        course1.setName("Test course 1");
        course1.setCode("TEST1");

        Course course2 = new Course();
        course1.setName("Test course 2");
        course1.setCode("TEST2");

        lenient().when(courseService.search(emptyFilter)).thenReturn(List.of(course, course1, course2));
        lenient().when(courseService.search(filter)).thenReturn(List.of(course2));
    }

    @Test
    public void testInit() {
        view.init();
        verify(view, times(1)).setSizeFull();
        verify(view, times(1)).configureToolbar();
        verify(view, times(1)).configureGrid();
        verify(view, times(1)).configureForm();
        assertEquals(2, view.getComponentCount());
        verify(view, times(1)).updateItems();
    }

    @Test
    public void testCloseForm() {
        view.closeForm();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testSaveCourse() {
        Course course = new Course();
        view.saveCourse(course);
        verify(courseService, times(1)).save(course);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course();
        view.deleteCourse(course);
        verify(courseService, times(1)).delete(course);
        verify(view, times(1)).updateItems();
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testConfigureForm() {
        view.configureForm();
        assertFalse(view.form.isVisible());
        verify(courseForm, times(1)).addSaveAction(any());
        verify(courseForm, times(1)).addDeleteAction(any());
        verify(courseForm, times(1)).addCancelAction(any());
    }

    @Test
    public void testConfigureGrid() {
        view.configureGrid();
        assertEquals("100%", view.grid.getWidth());
        assertEquals("100%", view.grid.getHeight());
        assertEquals(3, view.grid.getColumns().size());
        assertTrue(view.grid.getColumns().stream().allMatch(Grid.Column::isAutoWidth));

        Course course = new Course();
        view.grid.select(course);
        verify(view, times(1)).editCourse(course);
    }

    @Test
    public void testConfigureToolbar() {
        view.configureToolbar();
        String value = "test";
        view.textFilter.setValue(value);
        verify(view, times(1)).updateItems();
        assertEquals(ValueChangeMode.LAZY, view.textFilter.getValueChangeMode());
        assertTrue(view.textFilter.isClearButtonVisible());
        assertEquals("Filter by code or name...", view.textFilter.getPlaceholder());
    }

    @Test
    public void testCreateToolbar() {
        var toolbar = view.createToolbar();
        assertEquals(2, toolbar.getChildren().count());
    }

    @Test
    public void testCreateContent() {
        var content = view.createContent();
        assertEquals("100%", content.getElement().getStyle().get("width"));
        assertEquals("100%", content.getElement().getStyle().get("height"));
        assertEquals(2, content.getChildren().count());
    }

    @Test
    public void testCloseEditor() {
        view.closeEditor();
        verify(courseForm, times(1)).setFormBean(null);
        verify(courseForm, times(1)).setVisible(false);
    }

    @Test
    public void testEditCourse() {
        Course course = new Course();
        view.editCourse(course);
        verify(courseForm, times(1)).setFormBean(course);
        verify(courseForm, times(1)).setVisible(true);

        view.editCourse(null);
        verify(view, times(1)).closeEditor();
    }

    @Test
    public void testAddCourse() {
        Course course = new Course();
        view.grid.select(course);
        view.addCourse();
        assertTrue(view.grid.asSingleSelect().isEmpty());
        verify(view, times(1)).editCourse(course);
    }

    @Test
    public void testUpdateItems() {
        mockCourseSearch();
        view.updateItems();
        verify(courseService, times(1)).search(emptyFilter);
        assertEquals(3, view.grid.getDataProvider().size(new Query<>()));

        view.textFilter.setValue(filter);
        view.updateItems();
        verify(courseService, times(1)).search(filter);
        assertEquals(1, view.grid.getDataProvider().size(new Query<>()));
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
        assertTrue(view.addCourseBtn.isVisible());
    }

    @Test
    public void testGridPopulatedOnStart() {
        mockCourseSearch();
        view.init();
        assertEquals(3, view.grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testAddCourseButtonClicked() {
        view.addCourseBtn.click();
        verify(view, times(1)).addCourse();
    }
}
