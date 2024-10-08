package pl.bscisel.timetable.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import pl.bscisel.timetable.data.entity.ClassGroup;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {"spring.datasource.initialization-mode=never"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClassGroupRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ClassGroupRepository classGroupRepository;

    @Test
    @Sql("class_groups_data.sql")
    void testFindByOrganizationalUnitIdOrderByName() {
        List<ClassGroup> classGroups = classGroupRepository.findByOrganizationalUnitIdOrderByName(1L);

        assertEquals(3, classGroups.size());
        assertEquals("Class Group 1", classGroups.get(0).getName());
    }
}