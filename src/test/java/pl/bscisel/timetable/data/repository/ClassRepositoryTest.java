package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.Class;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // because @Sql script is not reset after each test don't know why
class ClassRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ClassRepository classRepository;

    @Test
    @Sql(value = "classes_data.sql")
    void testFindByOrganizationalUnitIdOrderByName() {
        List<Class> classes = classRepository.findAllByClassGroupId(2L);
        assertEquals(2, classes.size());
        assertEquals(LocalTime.of(10, 0), classes.get(0).getStartTime());
        classes = classRepository.findAllByClassGroupId(1L);
        assertEquals(1, classes.size());
        assertEquals(LocalTime.of(8, 0), classes.get(0).getStartTime());
        classes = classRepository.findAllByClassGroupId(3L);
        assertEquals(0, classes.size());
    }

    @Test
    @Sql(value = "classes_data.sql")
    void testFindAllByTeacherId() {
        List<Class> classes = classRepository.findAllByTeacherId(1L);
        assertEquals(1, classes.size());
        assertEquals(LocalTime.of(8, 0), classes.get(0).getStartTime());
        classes = classRepository.findAllByTeacherId(2L);
        assertEquals(2, classes.size());
        assertEquals(LocalTime.of(10, 0), classes.get(0).getStartTime());
    }

}