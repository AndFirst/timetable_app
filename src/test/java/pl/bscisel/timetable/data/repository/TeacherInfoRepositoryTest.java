package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.TeacherInfo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class TeacherInfoRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private TeacherInfoRepository teacherInfoRepository;

    @Test
    @Sql(value = "teacher_infos_data.sql")
    public void testFindByAccountId() {
        Optional<TeacherInfo> teacherInfos = teacherInfoRepository.findByAccountId(1L);
        assertTrue(teacherInfos.isPresent());
        assertEquals("Adam", teacherInfos.get().getName());
        assertEquals("Nowak", teacherInfos.get().getSurname());
    }

    @Test
    @Sql(value = "teacher_infos_data.sql")
    public void testFindByDegreeAndNameAndSurnameConcatenatedContaining() {
        List<TeacherInfo> teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("bc Adam Nowak");
        assertEquals(1, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("bc a");
        assertEquals(2, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("not existing");
        assertEquals(0, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("dam nowa");
        assertEquals(1, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("kowalsk");
        assertEquals(2, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("ada");
        assertEquals(1, teacherInfos.size());
        teacherInfos = teacherInfoRepository.findByDegreeAndNameAndSurnameConcatenatedContaining("bc");
        assertEquals(2, teacherInfos.size());
    }

    @Test
    @Sql(value = "teacher_infos_data.sql")
    public void testExistsByAccountId() {
        boolean exists = teacherInfoRepository.existsByAccountId(1L);
        assertTrue(exists);
        exists = teacherInfoRepository.existsByAccountId(2L);
        assertTrue(exists);
        exists = teacherInfoRepository.existsByAccountId(3L);
        assertFalse(exists);
    }

    @Test
    @Sql(value = "teacher_infos_data.sql")
    public void testExistsByAccountIdAndIdNot() {
        boolean exists = teacherInfoRepository.existsByAccountIdAndIdNot(1L, 2L);
        assertFalse(exists);
        exists = teacherInfoRepository.existsByAccountIdAndIdNot(1L, 1L);
        assertTrue(exists);
    }
}