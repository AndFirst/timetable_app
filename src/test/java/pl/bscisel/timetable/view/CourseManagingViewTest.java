package pl.bscisel.timetable.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.form.CourseForm;
import pl.bscisel.timetable.service.CourseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class CourseManagingViewTest {

    private CourseService courseService;
    private CourseForm courseForm;
    private CourseManagingView view;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        when(courseService.getEntityClass()).thenReturn(Course.class);

        courseForm = spy(CourseForm.class);
        courseForm.setCourseService(courseService);

        view = spy(CourseManagingView.class);
        view.setService(courseService);
        view.setForm(courseForm);
    }

    @Test
    public void configureFilter() {
        view.configureFilter();
        assertEquals("Filter by code or name...", view.textFilter.getPlaceholder());
    }

    @Test
    public void configureAddButton() {
        view.configureAddButton();
        assertEquals("Add course", view.addEntityButton.getText());
    }

    @Test
    public void setGridColumns() {
        view.configureGrid();
        view.setGridColumns();
        assertEquals(3, view.grid.getColumns().size());
    }

}
