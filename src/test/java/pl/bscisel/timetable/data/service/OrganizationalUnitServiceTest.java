package pl.bscisel.timetable.data.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.OrganizationalUnit;
import pl.bscisel.timetable.data.repository.OrganizationalUnitRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrganizationalUnitServiceTest {

    @Mock
    private OrganizationalUnitRepository organizationalUnitRepository;

    @InjectMocks
    private OrganizationalUnitService organizationalUnitService;

    @Test
    public void testFindTopLevelUnits() {
        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findByParentUnitNull()).thenReturn(expectedUnits);

        List<OrganizationalUnit> result = organizationalUnitService.findTopLevelUnits();

        assertEquals(expectedUnits, result);

        verify(organizationalUnitRepository, times(1)).findByParentUnitNull();
    }

    @Test
    public void testCountTopLevelUnits() {
        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findByParentUnitNull()).thenReturn(expectedUnits);

        int result = organizationalUnitService.countTopLevelUnits();

        assertEquals(expectedUnits.size(), result);

        verify(organizationalUnitRepository, times(1)).findByParentUnitNull();
    }

    @Test
    public void testFindChildrenByUnitId() {
        Long organizationalUnitId = 1L;

        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findByParentUnitId(organizationalUnitId)).thenReturn(expectedUnits);

        List<OrganizationalUnit> result = organizationalUnitService.findChildrenByUnitId(organizationalUnitId);

        assertEquals(expectedUnits, result);

        verify(organizationalUnitRepository, times(1)).findByParentUnitId(organizationalUnitId);
    }

    @Test
    public void testCountChildrenByUnitId() {
        Long organizationalUnitId = 1L;

        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findByParentUnitId(organizationalUnitId)).thenReturn(expectedUnits);

        int result = organizationalUnitService.countChildrenByUnitId(organizationalUnitId);

        assertEquals(expectedUnits.size(), result);

        verify(organizationalUnitRepository, times(1)).findByParentUnitId(organizationalUnitId);
    }

    @Test
    public void testHasChildrenByUnitId() {
        Long organizationalUnitId = 1L;

        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findByParentUnitId(organizationalUnitId)).thenReturn(expectedUnits);

        boolean result = organizationalUnitService.hasChildrenByUnitId(organizationalUnitId);

        assertTrue(result);

        verify(organizationalUnitRepository, times(1)).findByParentUnitId(organizationalUnitId);
    }

    @Test
    public void testFindAll() {
        List<OrganizationalUnit> expectedUnits = List.of(new OrganizationalUnit(), new OrganizationalUnit());
        when(organizationalUnitRepository.findAll()).thenReturn(expectedUnits);

        List<OrganizationalUnit> result = organizationalUnitService.findAll();

        assertEquals(expectedUnits, result);

        verify(organizationalUnitRepository, times(1)).findAll();
    }

    @Test
    public void testSaveOrganizationalUnit() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        when(organizationalUnitRepository.save(organizationalUnit)).thenReturn(organizationalUnit);

        organizationalUnitService.save(organizationalUnit);

        verify(organizationalUnitRepository, times(1)).save(organizationalUnit);
    }

    @Test
    public void testDeleteOrganizationalUnit() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        doNothing().when(organizationalUnitRepository).delete(organizationalUnit);

        organizationalUnitService.delete(organizationalUnit);

        verify(organizationalUnitRepository, times(1)).delete(organizationalUnit);
    }

    @Test
    public void testGetNameWithId() {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setId(1L);
        organizationalUnit.setName("Organizational Unit");

        String result = organizationalUnitService.getNameWithId(organizationalUnit);

        assertEquals("1. Organizational Unit", result);
    }

    @Test
    public void testOrganizationalUnitExistsByNameAndParentUnitNull() {
        String name = "Organizational Unit";
        String name2 = "Organizational Unit 2";

        OrganizationalUnit organizationalUnit1 = new OrganizationalUnit();
        organizationalUnit1.setId(1L);
        organizationalUnit1.setName(name);
        OrganizationalUnit organizationalUnit2 = new OrganizationalUnit();
        organizationalUnit2.setId(2L);
        organizationalUnit2.setName(name2);
        List<OrganizationalUnit> list = List.of(organizationalUnit1, organizationalUnit2);
        when(organizationalUnitRepository.findByParentUnitNull()).thenReturn(list);

        boolean result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name, null, null);
        assertTrue(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name, null, 1L);
        assertFalse(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name2, null, null);
        assertTrue(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name2, null, 2L);
        assertFalse(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId("  organizational unit 2  ", null, null);
        assertTrue(result);

        verify(organizationalUnitRepository, times(5)).findByParentUnitNull();
    }

    @Test
    public void testOrganizationalUnitExistsByNameAndParentUnitIdNotNull() {
        String name = "Organizational Unit";
        String name2 = "Organizational Unit 2";

        OrganizationalUnit organizationalUnit1 = new OrganizationalUnit();
        organizationalUnit1.setId(1L);
        organizationalUnit1.setName(name);
        OrganizationalUnit organizationalUnit2 = new OrganizationalUnit();
        organizationalUnit2.setId(2L);
        organizationalUnit2.setName(name2);

        List<OrganizationalUnit> list = List.of(organizationalUnit1, organizationalUnit2);
        Long parentUnitId = 1L;
        when(organizationalUnitRepository.findByParentUnitId(parentUnitId)).thenReturn(list);

        boolean result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name, parentUnitId, null);
        assertTrue(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name, parentUnitId, 1L);
        assertFalse(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name2, parentUnitId, null);
        assertTrue(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId(name2, parentUnitId, 2L);
        assertFalse(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId("  organizational unit  ", parentUnitId, null);
        assertTrue(result);
        result = organizationalUnitService.organizationalUnitExistsByNameAndParentUnitId("  organizational unit  ", parentUnitId, 1L);
        assertFalse(result);
    }
}
