package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClassTest {

    @Test
    public void ensureSetTypeStripsWhitespaces() {
        Class classObj = new Class();
        classObj.setType("  Type  ");
        assertEquals("Type", classObj.getType());
    }

    @Test
    public void testTypeSizeConstraints() {
        Class classObj = new Class();
        EntityTestUtils.testStringSizeConstraints(classObj, classObj::setType, "type", 0, 50);
    }

    @Test
    public void ensureCourseIsRequired() {
        Class classObj = new Class();
        assertEquals(1, EntityTestUtils.validator.validateProperty(classObj, "course").size());

        classObj.setCourse(new Course());
        assertEquals(0, EntityTestUtils.validator.validateProperty(classObj, "course").size());
    }

    @Test
    public void ensureClassGroupIsRequired() {
        Class classObj = new Class();
        assertEquals(1, EntityTestUtils.validator.validateProperty(classObj, "classGroup").size());

        classObj.setClassGroup(new ClassGroup());
        assertEquals(0, EntityTestUtils.validator.validateProperty(classObj, "classGroup").size());
    }

    @Test
    public void ensureFrequencyIsSetToDefault() {
        Class classObj = new Class();
        assertEquals(Class.ClassFrequency.ALL_WEEKS, classObj.getFrequency());
    }

    @Test
    public void ensureFrequencyCharIsSetCorrectly() {
        Class classObj = new Class();
        assertEquals('A', classObj.frequency);

        classObj.setFrequency(Class.ClassFrequency.ODD_WEEKS);
        assertEquals('O', classObj.frequency);

        classObj.setFrequency(Class.ClassFrequency.EVEN_WEEKS);
        assertEquals('E', classObj.frequency);

        classObj.setFrequency(Class.ClassFrequency.ALL_WEEKS);
        assertEquals('A', classObj.frequency);
    }

    @Test
    public void testFrequencyLabel() {
        assertEquals("Odd weeks", Class.ClassFrequency.ODD_WEEKS.getLabel());
        assertEquals("Even weeks", Class.ClassFrequency.EVEN_WEEKS.getLabel());
        assertEquals("All weeks", Class.ClassFrequency.ALL_WEEKS.getLabel());
    }

    @Test
    public void testFrequencyFromSymbol() {
        assertEquals(Class.ClassFrequency.ODD_WEEKS, Class.ClassFrequency.fromSymbol('O'));
        assertEquals(Class.ClassFrequency.EVEN_WEEKS, Class.ClassFrequency.fromSymbol('E'));
        assertEquals(Class.ClassFrequency.ALL_WEEKS, Class.ClassFrequency.fromSymbol('A'));
        assertNull(Class.ClassFrequency.fromSymbol('X'));
    }
}