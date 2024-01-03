package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CourseTest {

    @Test
    public void ensureSetCodeStripsWhitespaces() {
        Course course = new Course();
        course.setCode("  Code  ");
        assertEquals("Code", course.getCode());
    }

    @Test
    public void testCodeSizeConstraints() {
        Course course = new Course();
        EntityTestUtils.testStringSizeConstraints(course, course::setCode, "code", 2, 50);
    }

    @Test
    public void ensureSetNameStripsWhitespaces() {
        Course course = new Course();
        course.setName("  Course 1  ");
        assertEquals("Course 1", course.getName());
    }

    @Test
    public void testNameSizeConstraints() {
        Course course = new Course();
        EntityTestUtils.testStringSizeConstraints(course, course::setName, "name", 3, 100);
    }

    @Test
    public void testDescriptionSizeConstraints() {
        Course course = new Course();
        EntityTestUtils.testStringSizeConstraints(course, course::setDescription, "description", 0, 500);
    }

    @Test
    public void ensureValidCourseIsValid() {
        Course course = new Course();
        course.setCode("Code");
        course.setName("Course 1");
        assertEquals(0, EntityTestUtils.validator.validate(course).size());
    }
}