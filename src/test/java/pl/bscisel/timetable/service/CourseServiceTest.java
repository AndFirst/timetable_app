package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.Course;
import pl.bscisel.timetable.data.repository.CourseRepository;
import pl.bscisel.timetable.service.CourseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testSave() {
        Course course = new Course();
        when(courseRepository.save(course)).thenReturn(course);

        courseService.save(course);

        verify(courseRepository, times(1)).save(course);
    }

    @Test
    public void testDelete() {
        Course course = new Course();
        doNothing().when(courseRepository).delete(course);

        courseService.delete(course);

        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    public void testSearch() {
        String filter = "filter";
        List<Course> expectedCoursesFilter = List.of(new Course());
        List<Course> expectedCoursesFindAll = List.of(new Course(), new Course());
        when(courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(filter, filter)).thenReturn(expectedCoursesFilter);
        when(courseRepository.findAll()).thenReturn(expectedCoursesFindAll);

        List<Course> result = courseService.search(filter);
        assertEquals(expectedCoursesFilter, result);
        verify(courseRepository, times(1)).findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(filter, filter);

        result = courseService.search(null);
        assertEquals(expectedCoursesFindAll, result);

        result = courseService.search("");
        assertEquals(expectedCoursesFindAll, result);

        result = courseService.search(" ");
        assertEquals(expectedCoursesFindAll, result);
        verify(courseRepository, times(3)).findAll();
    }

    @Test
    public void testExistsByCode() {
        String code = " ABC ";
        Long excludeId = 1L;
        Course course = new Course();
        course.setId(excludeId);
        when(courseRepository.existsByCodeIgnoreCase(code.strip())).thenReturn(true);
        when(courseRepository.existsByCodeIgnoreCaseAndIdNot("ABC", excludeId)).thenReturn(false);

        boolean result = courseService.existsByCode(null, null);
        assertFalse(result);
        verify(courseRepository, times(0)).existsByCodeIgnoreCaseAndIdNot(code, null);

        result = courseService.existsByCode(null, excludeId);
        assertFalse(result);
        verify(courseRepository, times(0)).existsByCodeIgnoreCaseAndIdNot(code, excludeId);

        result = courseService.existsByCode("", null);
        assertFalse(result);
        verify(courseRepository, times(0)).existsByCodeIgnoreCaseAndIdNot(code, null);

        result = courseService.existsByCode(" ", null);
        assertFalse(result);
        verify(courseRepository, times(0)).existsByCodeIgnoreCaseAndIdNot(code, null);

        result = courseService.existsByCode(code, null);
        assertTrue(result);
        verify(courseRepository, times(1)).existsByCodeIgnoreCase(code.strip());

        result = courseService.existsByCode(code, excludeId);
        assertFalse(result);
        verify(courseRepository, times(1)).existsByCodeIgnoreCaseAndIdNot(code.strip(), excludeId);
    }

    @Test
    public void testFindAll() {
        List<Course> list = List.of(new Course(), new Course());
        when(courseRepository.findAll()).thenReturn(list);

        List<Course> result = courseService.findAll();

        assertEquals(list, result);

        verify(courseRepository, times(1)).findAll();
    }
}
