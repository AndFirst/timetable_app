package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.ClassGroup;
import pl.bscisel.timetable.data.repository.ClassGroupRepository;
import pl.bscisel.timetable.service.ClassGroupService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClassGroupServiceTest {

    @Mock
    private ClassGroupRepository classGroupRepository;

    @InjectMocks
    private ClassGroupService classGroupService;

    @Test
    public void testFindByOrganizationalUnitId() {
        Long organizationalUnitId = 1L;

        List<ClassGroup> expectedGroups = List.of(new ClassGroup(), new ClassGroup());
        when(classGroupRepository.findByOrganizationalUnitIdOrderByName(organizationalUnitId)).thenReturn(expectedGroups);

        List<ClassGroup> result = classGroupService.findByOrganizationalUnitId(organizationalUnitId);

        assertEquals(expectedGroups, result);

        verify(classGroupRepository, times(1)).findByOrganizationalUnitIdOrderByName(organizationalUnitId);
    }

    @Test
    public void testSave() {
        ClassGroup classGroup = new ClassGroup();
        when(classGroupRepository.save(classGroup)).thenReturn(classGroup);

        classGroupService.save(classGroup);

        verify(classGroupRepository, times(1)).save(classGroup);
    }

    @Test
    public void testDelete() {
        ClassGroup classGroup = new ClassGroup();
        doNothing().when(classGroupRepository).delete(classGroup);

        classGroupService.delete(classGroup);

        verify(classGroupRepository, times(1)).delete(classGroup);
    }

    @Test
    public void testExistsByNameAndOrganizationalUnitId() {
        ClassGroup classGroup1 = new ClassGroup();
        long classGroup1Id = 1L;
        classGroup1.setId(classGroup1Id);
        classGroup1.setName("Class Group 1");
        ClassGroup classGroup2 = new ClassGroup();
        classGroup2.setId(2L);
        classGroup2.setName("Class Group 2");
        List<ClassGroup> list = List.of(classGroup1, classGroup2);
        when(classGroupRepository.findByOrganizationalUnitIdOrderByName(1L)).thenReturn(list);

        boolean result = classGroupService.existsByNameAndOrganizationalUnitId("Class Group 1", 1L, null);
        assertTrue(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("Class Group 2", 1L, null);
        assertTrue(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("  class group 2  ", 1L, null);
        assertTrue(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("Class Group 3", 1L, null);
        assertFalse(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("Class Group 1", 1L, classGroup1Id);
        assertFalse(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("Class Group 1", 1L, 1000L);
        assertTrue(result);
        result = classGroupService.existsByNameAndOrganizationalUnitId("  class group 2  ", 1L, 1000L);
        assertTrue(result);

        verify(classGroupRepository, times(7)).findByOrganizationalUnitIdOrderByName(1L);
    }

    @Test
    public void testFindAll() {
        List<ClassGroup> list = List.of(new ClassGroup(), new ClassGroup());
        when(classGroupRepository.findAll()).thenReturn(list);

        List<ClassGroup> result = classGroupService.findAll();

        assertEquals(list, result);

        verify(classGroupRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Long classGroupId = 1L;
        ClassGroup classGroup = new ClassGroup();
        when(classGroupRepository.findById(classGroupId)).thenReturn(java.util.Optional.of(classGroup));

        Optional<ClassGroup> result = classGroupService.findById(classGroupId);

        assertEquals(Optional.of(classGroup), result);

        verify(classGroupRepository, times(1)).findById(classGroupId);
    }
}
