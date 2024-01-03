package pl.bscisel.timetable.data.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import pl.bscisel.timetable.data.entity.utils.EntityTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class OrganizationalUnitTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void ensureSetNameStripsWhitespaces() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("  Organizational Unit 1  ");
        assertEquals("Organizational Unit 1", organizationalUnit.getName());
    }

    @Test
    public void testNameSizeConstraints() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        EntityTestUtils.testStringSizeConstraints(organizationalUnit, organizationalUnit::setName, "name", 2, 100);
    }

    @Test
    public void testDescriptionSizeConstraints() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        EntityTestUtils.testStringSizeConstraints(organizationalUnit, organizationalUnit::setDescription, "description", 0, 1000);
    }

    @Test
    @Transactional
    public void testUpdateIsTopLevelByte() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        entityManager.persist(organizationalUnit);

        assertEquals((byte) 1, organizationalUnit.getIsTopLevel());

        OrganizationalUnit parentUnit = new OrganizationalUnit();
        parentUnit.setName("Parent Unit");
        entityManager.persist(parentUnit);

        organizationalUnit.setParentUnit(parentUnit);
        entityManager.merge(organizationalUnit);
        entityManager.flush();
        assertNull(organizationalUnit.getIsTopLevel());
    }

    @Test
    public void testClone() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        organizationalUnit.setDescription("Description 1");
        OrganizationalUnit organizationalUnitClone = organizationalUnit.clone();
        assertEquals(organizationalUnit.getName(), organizationalUnitClone.getName());
        assertEquals(organizationalUnit.getDescription(), organizationalUnitClone.getDescription());
    }

    @Test
    public void ensureValidOrganizationalUnitIsValid() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setName("Organizational Unit 1");
        assertEquals(0, EntityTestUtils.validator.validate(organizationalUnit).size());
    }

}