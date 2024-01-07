package pl.bscisel.timetable.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import pl.bscisel.timetable.data.entity.TeacherInfo;
import pl.bscisel.timetable.data.repository.TeacherInfoRepository;
import pl.bscisel.timetable.service.TeacherInfoService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TeacherInfoServiceTest {

    @Mock
    private TeacherInfoRepository teacherInfoRepository;

    @InjectMocks
    private TeacherInfoService teacherInfoService;

    @Test
    public void testFindAll() {
        List<TeacherInfo> expectedTeacherInfos = List.of(new TeacherInfo(), new TeacherInfo());
        when(teacherInfoRepository.findAll()).thenReturn(expectedTeacherInfos);

        List<TeacherInfo> result = teacherInfoService.findAll();

        assertEquals(expectedTeacherInfos, result);

        verify(teacherInfoRepository, times(1)).findAll();
    }

    @Test
    public void testFindByUserId() {
        TeacherInfo teacher = new TeacherInfo();

        when(teacherInfoRepository.findByUserId(1L)).thenReturn(Optional.of(teacher));

        Optional<TeacherInfo> result = teacherInfoService.findByUserId(1L);

        assertEquals(Optional.of(teacher), result);

        verify(teacherInfoRepository, times(1)).findByUserId(1L);
    }

    @Test
    public void testFindById() {
        TeacherInfo teacher = new TeacherInfo();

        when(teacherInfoRepository.findById(1L)).thenReturn(Optional.of(teacher));

        Optional<TeacherInfo> result = teacherInfoService.findById(1L);

        assertEquals(Optional.of(teacher), result);

        verify(teacherInfoRepository, times(1)).findById(1L);
    }

    @Test
    public void testSave() {
        TeacherInfo teacher = new TeacherInfo();
        when(teacherInfoRepository.save(teacher)).thenReturn(teacher);

        teacherInfoService.save(teacher);

        verify(teacherInfoRepository, times(1)).save(teacher);
    }

    @Test
    public void testDelete() {
        TeacherInfo teacher = new TeacherInfo();
        doNothing().when(teacherInfoRepository).delete(teacher);

        teacherInfoService.delete(teacher);

        verify(teacherInfoRepository, times(1)).delete(teacher);
    }

    @Test
    public void testSearch() {
        String filter = "filter";
        List<TeacherInfo> expectedTeacherInfosFilter = List.of(new TeacherInfo());
        List<TeacherInfo> expectedTeacherInfosFindAll = List.of(new TeacherInfo(), new TeacherInfo());
        when(teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining(filter)).thenReturn(expectedTeacherInfosFilter);
        when(teacherInfoRepository.findAll()).thenReturn(expectedTeacherInfosFindAll);

        List<TeacherInfo> result = teacherInfoService.search(filter);
        assertEquals(expectedTeacherInfosFilter, result);
        verify(teacherInfoRepository, times(1)).findByDegreeAndNameAndSurnameConcatenatedContaining(filter);

        result = teacherInfoService.search(null);
        assertEquals(expectedTeacherInfosFindAll, result);

        result = teacherInfoService.search("");
        assertEquals(expectedTeacherInfosFindAll, result);

        result = teacherInfoService.search(" ");
        assertEquals(expectedTeacherInfosFindAll, result);
        verify(teacherInfoRepository, times(3)).findAll();
    }

    @Test
    public void testExistsByUserId() {
        when(teacherInfoRepository.existsByUserId(1L)).thenReturn(true);
        when(teacherInfoRepository.existsByUserIdAndIdNot(1L, 1L)).thenReturn(false);

        boolean result = teacherInfoService.existsByUserId(1L, null);
        assertTrue(result);
        result = teacherInfoService.existsByUserId(1L, 1L);
        assertFalse(result);

        verify(teacherInfoRepository, times(1)).existsByUserId(1L);
        verify(teacherInfoRepository, times(1)).existsByUserIdAndIdNot(1L, 1L);
    }

}
