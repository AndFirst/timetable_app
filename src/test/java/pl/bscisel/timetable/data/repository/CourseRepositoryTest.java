package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.Course;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class CourseRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Sql(value = "courses_data.sql")
    void testfindByCodeContainsIgnoreCaseOrNameContainsIgnoreCase() {
        List<Course> courses = courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase("B", "B");
        assertEquals(1, courses.size());
        assertEquals("ABC Course", courses.get(0).getName());
        courses = courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase("A", "A");
        assertEquals(2, courses.size());
        assertEquals("ABC Course", courses.get(0).getName());
        assertEquals("ADD Course", courses.get(1).getName());
        courses = courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase("Z", "Z");
        assertEquals(0, courses.size());
        courses = courseRepository.findByCodeContainsIgnoreCaseOrNameContainsIgnoreCase("Course", "Course");
        assertEquals(5, courses.size());
    }

    @Test
    @Sql(value = "courses_data.sql")
    void testExistsByCodeIgnoreCaseAndIdNot() {
        boolean exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("ABC", 1L);
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("ABC", 2L);
        assertTrue(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("abc", 1L);
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("abc", 2L);
        assertTrue(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("ABCD", 1L);
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("abcd", 1L);
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("ABCD", 2L);
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCaseAndIdNot("abcd", 2L);
        assertFalse(exists);
    }

    @Test
    @Sql(value = "courses_data.sql")
    void testExistsByCodeIgnoreCase() {
        boolean exists = courseRepository.existsByCodeIgnoreCase("ABC");
        assertTrue(exists);
        exists = courseRepository.existsByCodeIgnoreCase("abc");
        assertTrue(exists);
        exists = courseRepository.existsByCodeIgnoreCase("ABCD");
        assertFalse(exists);
        exists = courseRepository.existsByCodeIgnoreCase("abcd");
        assertFalse(exists);
    }
}