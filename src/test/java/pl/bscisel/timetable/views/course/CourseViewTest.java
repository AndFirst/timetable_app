package pl.bscisel.timetable.views.course;

import com.vaadin.flow.data.provider.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.service.CourseService;
import pl.bscisel.timetable.views.course.forms.CourseForm;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CourseViewTest {

    private final String emptyFilter = "";
    private final String filter = "TEST";

    CourseService courseService;
    CourseForm courseForm;
    CourseView courseView;

    @BeforeEach
    public void setUp() {
        courseService = mock(CourseService.class);
        mockCourseSearch();
        courseForm = spy(new CourseForm(courseService));
        courseView = spy(new CourseView(courseService, courseForm));
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

        lenient().when(courseService.search(emptyFilter)).thenReturn(List.of(course, course1));
        lenient().when(courseService.search(filter)).thenReturn(List.of(course2));
    }

    @Test
    public void testFormNotVisibleOnStart() {
        assertFalse(courseForm.isVisible());
    }

    @Test
    public void testGridIsVisibleOnStart() {
        assertTrue(courseView.grid.isVisible());
    }

    @Test
    public void testToolbarIsVisibleOnStart() {
        assertTrue(courseView.textFilter.isVisible());
        assertTrue(courseView.addCourseBtn.isVisible());
    }

    @Test
    public void testGridPopulatedOnStart() {
        assertEquals(2, courseView.grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testShowFormOnAddCourseClick() {
        courseView.addCourseBtn.click();
        assertTrue(courseForm.isVisible());
    }

    @Test
    public void testCloseForm() {
        courseView.addCourseBtn.click();
        assertTrue(courseForm.isVisible());
        courseView.closeForm();
        assertFalse(courseForm.isVisible());
        verify(courseForm, times(1)).setFormBean(null);
    }

    @Test
    public void testBeanIsCleanedOnCloseForm() {
        courseView.addCourseBtn.click();
        verify(courseForm, times(1)).setFormBean(any());
        courseView.closeForm();
        verify(courseForm, times(1)).setFormBean(null);
    }

    @Test
    public void testFormActionsAdded() {
        verify(courseForm, times(1)).addCancelAction(any());
        verify(courseForm, times(1)).addDeleteAction(any());
        verify(courseForm, times(1)).addSaveAction(any());
    }

    @Test
    public void testEditCourse() {
        assertFalse(courseView.form.isVisible());
        Course course = new Course();
        courseView.editCourse(course);
        assertTrue(courseView.form.isVisible());
        verify(courseForm, times(1)).setFormBean(course);

        courseView.editCourse(null);
        assertFalse(courseView.form.isVisible());
        verify(courseForm, times(1)).setFormBean(null);
    }

    @Test
    public void testSaveCourse() {
        Course course = new Course();
        courseView.saveCourse(course);
        verify(courseService, times(1)).save(course);
        verify(courseView, times(1)).updateItems();
        verify(courseView, times(1)).closeEditor();
    }

    @Test
    public void testDeleteCourse() {
        Course course = new Course();
        courseView.deleteCourse(course);
        verify(courseService, times(1)).delete(course);
        verify(courseView, times(1)).updateItems();
        verify(courseView, times(1)).closeEditor();
    }

    @Test
    public void testAddCourse() {
        courseView.addCourse();
        verify(courseForm, times(1)).setFormBean(any());
        verify(courseForm, times(1)).setVisible(true);
        assertTrue(courseView.grid.asSingleSelect().isEmpty());
    }

    @Test
    public void testUpdateItems() {
        courseView.updateItems();
        verify(courseService, times(2)).search(emptyFilter); // 1 from setUp, 1 from updateItems
        assertEquals(2, courseView.grid.getDataProvider().size(new Query<>()));
    }

    @Test
    public void testFiltering() {
        courseView.textFilter.setValue(filter);
        verify(courseService, times(1)).search(filter);
        assertEquals(1, courseView.grid.getDataProvider().size(new Query<>()));
    }
}
