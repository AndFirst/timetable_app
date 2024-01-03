package pl.bscisel.timetable.data.entity;

import org.junit.jupiter.api.Test;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassGroupTest {

    @Test
    public void ensureSetNameStripsWhitespaces() {
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("  Class Group 1  ");
        assertEquals("Class Group 1", classGroup.getName());
    }

    @Test
    public void testNameSizeConstraints() {
        ClassGroup classGroup = new ClassGroup();
        EntityTestUtils.testStringSizeConstraints(classGroup, classGroup::setName, "name", 2, 50);
    }

    @Test
    public void testDescriptionSizeConstraints() {
        ClassGroup classGroup = new ClassGroup();
        EntityTestUtils.testStringSizeConstraints(classGroup, classGroup::setDescription, "description", 0, 500);
    }

    @Test
    public void ensureOrganizationalUnitIsRequired() {
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("Class Group 1");
        assertEquals(1, EntityTestUtils.validator.validate(classGroup).size());

        classGroup.setOrganizationalUnit(new OrganizationalUnit());
        assertEquals(0, EntityTestUtils.validator.validate(classGroup).size());
    }

    @Test
    public void ensureValidClassGroupIsValid() {
        ClassGroup classGroup = new ClassGroup();
        classGroup.setName("Class Group 1");
        classGroup.setOrganizationalUnit(new OrganizationalUnit());
        assertEquals(0, EntityTestUtils.validator.validate(classGroup).size());
    }
}