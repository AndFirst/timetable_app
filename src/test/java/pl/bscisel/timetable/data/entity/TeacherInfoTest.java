package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TeacherInfoTest {

    @Test
    public void ensureSetNameStripsWhitespace() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setName("  Jan  ");
        assertEquals("Jan", teacher.getName());
    }

    @Test
    public void ensureSetSurnameStripsWhitespace() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setSurname("  Kowalski  ");
        assertEquals("Kowalski", teacher.getSurname());
    }

    @Test
    public void ensureSetDegreeStripsWhitespace() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setDegree("  mgr  ");
        assertEquals("mgr", teacher.getDegree());
    }

    @Test
    public void ensureSetPhoneNumberStripsWhitespace() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setPhoneNumber("  123456789  ");
        assertEquals("123456789", teacher.getPhoneNumber());
    }

    @Test
    public void testNameSizeConstraints() {
        TeacherInfo teacher = new TeacherInfo();
        EntityTestUtils.testStringSizeConstraints(teacher, teacher::setName, "name", 3, 50);
    }

    @Test
    public void testSurnameSizeConstraints() {
        TeacherInfo teacher = new TeacherInfo();
        EntityTestUtils.testStringSizeConstraints(teacher, teacher::setSurname, "surname", 3, 50);
    }

    @Test
    public void testDegreeSizeConstraints() {
        TeacherInfo teacher = new TeacherInfo();
        EntityTestUtils.testStringSizeConstraints(teacher, teacher::setDegree, "degree", 0, 20);
    }

    @Test
    public void testPhoneNumberSizeConstraints() {
        TeacherInfo teacher = new TeacherInfo();
        EntityTestUtils.testStringSizeConstraints(teacher, teacher::setPhoneNumber, "phoneNumber", 0, 15);
    }

    @Test
    public void testGetFullName() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setName("Jan");
        teacher.setSurname("Kowalski");
        teacher.setDegree("mgr");
        assertEquals("mgr Jan Kowalski", teacher.getFullName());
    }

    @Test
    public void ensureValidTeacherInfoIsValid() {
        TeacherInfo teacher = new TeacherInfo();
        teacher.setName("Jan");
        teacher.setSurname("Kowalski");
        assertEquals(0, EntityTestUtils.validator.validate(teacher).size());
    }
}